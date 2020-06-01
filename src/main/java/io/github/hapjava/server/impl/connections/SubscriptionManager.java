package io.github.hapjava.server.impl.connections;

import io.github.hapjava.characteristics.EventableCharacteristic;
import io.github.hapjava.server.impl.http.HomekitClientConnection;
import io.github.hapjava.server.impl.http.HttpResponse;
import io.github.hapjava.server.impl.json.EventController;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SubscriptionManager {

  private static final Logger LOGGER = LoggerFactory.getLogger(SubscriptionManager.class);

  private final ConcurrentMap<EventableCharacteristic, Set<HomekitClientConnection>> subscriptions =
      new ConcurrentHashMap<>();
  private final ConcurrentMap<HomekitClientConnection, Set<EventableCharacteristic>> reverse =
      new ConcurrentHashMap<>();
  private final ConcurrentMap<HomekitClientConnection, ArrayList<PendingNotification>>
      pendingNotifications = new ConcurrentHashMap<>();
  private int nestedBatches = 0;

  public synchronized void addSubscription(
      int aid,
      int iid,
      EventableCharacteristic characteristic,
      HomekitClientConnection connection) {
    synchronized (this) {
      if (!subscriptions.containsKey(characteristic)) {
        subscriptions.putIfAbsent(characteristic, newSet());
      }
      subscriptions.get(characteristic).add(connection);
      if (subscriptions.get(characteristic).size() == 1) {
        characteristic.subscribe(
            () -> {
              publish(aid, iid, characteristic);
            });
      }

      if (!reverse.containsKey(connection)) {
        reverse.putIfAbsent(connection, newSet());
      }
      reverse.get(connection).add(characteristic);
      LOGGER.trace(
          "Added subscription to " + characteristic.getClass() + " for " + connection.hashCode());
    }
  }

  public synchronized void removeSubscription(
      EventableCharacteristic characteristic, HomekitClientConnection connection) {
    Set<HomekitClientConnection> subscriptions = this.subscriptions.get(characteristic);
    if (subscriptions != null) {
      subscriptions.remove(connection);
      if (subscriptions.size() == 0) {
        characteristic.unsubscribe();
      }
    }

    Set<EventableCharacteristic> reverse = this.reverse.get(connection);
    if (reverse != null) {
      reverse.remove(characteristic);
    }
    LOGGER.trace(
        "Removed subscription to " + characteristic.getClass() + " for " + connection.hashCode());
  }

  public synchronized void removeConnection(HomekitClientConnection connection) {
    Set<EventableCharacteristic> characteristics = reverse.remove(connection);
    pendingNotifications.remove(connection);
    if (characteristics != null) {
      for (EventableCharacteristic characteristic : characteristics) {
        Set<HomekitClientConnection> characteristicSubscriptions =
            subscriptions.get(characteristic);
        characteristicSubscriptions.remove(connection);
        if (characteristicSubscriptions.isEmpty()) {
          LOGGER.trace("Unsubscribing from characteristic as all subscriptions are closed");
          characteristic.unsubscribe();
          subscriptions.remove(characteristic);
        }
      }
    }
    LOGGER.trace("Removed connection {}", connection.hashCode());
  }

  private <T> Set<T> newSet() {
    return Collections.newSetFromMap(new ConcurrentHashMap<T, Boolean>());
  }

  public synchronized void batchUpdate() {
    ++this.nestedBatches;
  }

  public synchronized void completeUpdateBatch() {
    if (--this.nestedBatches == 0 && !pendingNotifications.isEmpty()) {
      LOGGER.trace("Publishing batched changes");
      for (ConcurrentMap.Entry<HomekitClientConnection, ArrayList<PendingNotification>> entry :
          pendingNotifications.entrySet()) {
        try {
          HttpResponse message = new EventController().getMessage(entry.getValue());
          entry.getKey().outOfBand(message);
        } catch (Exception e) {
          LOGGER.warn("Failed to create new event message", e);
        }
      }
      pendingNotifications.clear();
    }
  }

  public synchronized void publish(int accessoryId, int iid, EventableCharacteristic changed) {
    final Set<HomekitClientConnection> subscribers = subscriptions.get(changed);
    if ((subscribers == null) || (subscribers.isEmpty())) {
      LOGGER.debug("No subscribers to characteristic {} at accessory {} ", changed, accessoryId);
      return; // no subscribers
    }
    if (nestedBatches != 0) {
      LOGGER.trace("Batching change for accessory {} and characteristic {} " + accessoryId, iid);
      PendingNotification notification = new PendingNotification(accessoryId, iid, changed);
      for (HomekitClientConnection connection : subscribers) {
        if (!pendingNotifications.containsKey(connection)) {
          pendingNotifications.put(connection, new ArrayList<PendingNotification>());
        }
        pendingNotifications.get(connection).add(notification);
      }
      return;
    }

    try {
      HttpResponse message = new EventController().getMessage(accessoryId, iid, changed);
      LOGGER.trace("Publishing change for " + accessoryId);
      for (HomekitClientConnection connection : subscribers) {
        connection.outOfBand(message);
      }
    } catch (Exception e) {
      LOGGER.warn("Failed to create new event message", e);
    }
  }

  /** Remove all existing subscriptions */
  public void removeAll() {
    LOGGER.trace("Removing {} reverse connections from subscription manager", reverse.size());
    Iterator<HomekitClientConnection> i = reverse.keySet().iterator();
    while (i.hasNext()) {
      HomekitClientConnection connection = i.next();
      LOGGER.trace("Removing connection {}", connection.hashCode());
      removeConnection(connection);
    }
    LOGGER.trace("Subscription sizes are {} and {}", reverse.size(), subscriptions.size());
  }
}
