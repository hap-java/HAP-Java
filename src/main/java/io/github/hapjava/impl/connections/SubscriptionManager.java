package io.github.hapjava.impl.connections;

import io.github.hapjava.characteristics.EventableCharacteristic;
import io.github.hapjava.impl.http.HomekitClientConnection;
import io.github.hapjava.impl.http.HttpResponse;
import io.github.hapjava.impl.json.EventController;
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
      LOGGER.debug(
          "Added subscription to " + characteristic.getClass() + " for " + connection.hashCode());
    }
    /**
     * try { connection.outOfBand(new EventController().getMessage(aid, iid, characteristic)); }
     * catch (Exception e) { LOGGER.error("Could not send initial state in response to subscribe
     * event", e); }
     */
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
    LOGGER.debug(
        "Removed subscription to " + characteristic.getClass() + " for " + connection.hashCode());
  }

  public synchronized void removeConnection(HomekitClientConnection connection) {
    Set<EventableCharacteristic> characteristics = reverse.remove(connection);
    if (characteristics != null) {
      for (EventableCharacteristic characteristic : characteristics) {
        Set<HomekitClientConnection> characteristicSubscriptions =
            subscriptions.get(characteristic);
        characteristicSubscriptions.remove(connection);
        if (characteristicSubscriptions.isEmpty()) {
          LOGGER.debug("Unsubscribing from characteristic as all subscriptions are closed");
          characteristic.unsubscribe();
          subscriptions.remove(characteristic);
        }
      }
    }
    LOGGER.debug("Removed connection {}", connection.hashCode());
  }

  private <T> Set<T> newSet() {
    return Collections.newSetFromMap(new ConcurrentHashMap<T, Boolean>());
  }

  public void publish(int accessoryId, int iid, EventableCharacteristic changed) {
    try {
      HttpResponse message = new EventController().getMessage(accessoryId, iid, changed);
      LOGGER.debug("Publishing changes for " + accessoryId);
      for (HomekitClientConnection connection : subscriptions.get(changed)) {
        connection.outOfBand(message);
      }
    } catch (Exception e) {
      LOGGER.error("Failed to create new event message", e);
    }
  }

  /** Remove all existing subscriptions */
  public void removeAll() {
    LOGGER.debug("Removing {} reverse connections from subscription manager", reverse.size());
    Iterator<HomekitClientConnection> i = reverse.keySet().iterator();
    while (i.hasNext()) {
      HomekitClientConnection connection = i.next();
      LOGGER.debug("Removing connection {}", connection.hashCode());
      removeConnection(connection);
    }
    LOGGER.debug("Subscription sizes are {} and {}", reverse.size(), subscriptions.size());
  }
}
