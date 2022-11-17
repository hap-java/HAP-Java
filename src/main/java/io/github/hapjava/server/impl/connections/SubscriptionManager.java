package io.github.hapjava.server.impl.connections;

import io.github.hapjava.characteristics.Characteristic;
import io.github.hapjava.characteristics.EventableCharacteristic;
import io.github.hapjava.server.impl.HomekitRegistry;
import io.github.hapjava.server.impl.http.HomekitClientConnection;
import io.github.hapjava.server.impl.http.HttpResponse;
import io.github.hapjava.server.impl.json.EventController;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SubscriptionManager {

  private static final Logger LOGGER = LoggerFactory.getLogger(SubscriptionManager.class);

  private static class ConnectionsWithIds {
    Set<HomekitClientConnection> connections;
    int aid, iid;

    ConnectionsWithIds(int aid, int iid) {
      this.aid = aid;
      this.iid = iid;
      this.connections = new HashSet<>();
    }
  }

  private final Map<EventableCharacteristic, ConnectionsWithIds> subscriptions = new HashMap<>();
  private final Map<HomekitClientConnection, Set<EventableCharacteristic>> reverse =
      new HashMap<>();
  private final Map<HomekitClientConnection, List<PendingNotification>> pendingNotifications =
      new HashMap<>();
  private int nestedBatches = 0;

  public synchronized void addSubscription(
      int aid,
      int iid,
      EventableCharacteristic characteristic,
      HomekitClientConnection connection) {
    synchronized (this) {
      ConnectionsWithIds subscribers;
      if (subscriptions.containsKey(characteristic)) {
        subscribers = subscriptions.get(characteristic);
      } else {
        subscribers = new ConnectionsWithIds(aid, iid);
        subscriptions.put(characteristic, subscribers);
        subscribe(aid, iid, characteristic);
      }
      subscribers.connections.add(connection);

      if (!reverse.containsKey(connection)) {
        reverse.put(connection, new HashSet<>());
      }
      reverse.get(connection).add(characteristic);
      LOGGER.trace(
          "Added subscription to {}:{} ({}) for {}",
          aid,
          iid,
          characteristic.getClass().getSimpleName(),
          connection.hashCode());
    }
  }

  public synchronized void removeSubscription(
      EventableCharacteristic characteristic, HomekitClientConnection connection) {
    ConnectionsWithIds subscribers = subscriptions.get(characteristic);
    if (subscribers != null) {
      subscribers.connections.remove(connection);
      if (subscribers.connections.isEmpty()) {
        LOGGER.trace("Unsubscribing from characteristic as all subscriptions are closed");
        characteristic.unsubscribe();
        subscriptions.remove(characteristic);
      }

      // Remove pending notifications for this no-longer-subscribed characteristic
      List<PendingNotification> connectionNotifications = pendingNotifications.get(connection);
      if (connectionNotifications != null) {
        connectionNotifications.removeIf(n -> n.aid == subscribers.aid && n.iid == subscribers.iid);
        if (connectionNotifications.isEmpty()) pendingNotifications.remove(connection);
      }

      LOGGER.trace(
          "Removed subscription from {}:{} ({}) for {}",
          subscribers.aid,
          subscribers.iid,
          characteristic.getClass().getSimpleName(),
          connection.hashCode());
    }

    Set<EventableCharacteristic> reverse = this.reverse.get(connection);
    if (reverse != null) {
      reverse.remove(characteristic);
      if (reverse.isEmpty()) this.reverse.remove(connection);
    }
  }

  public synchronized void removeConnection(HomekitClientConnection connection) {
    removeConnection(connection, reverse.remove(connection));
  }

  private void removeConnection(
      HomekitClientConnection connection, Set<EventableCharacteristic> characteristics) {
    pendingNotifications.remove(connection);
    if (characteristics != null) {
      for (EventableCharacteristic characteristic : characteristics) {
        removeSubscription(characteristic, connection);
      }
    }
    LOGGER.trace("Removed connection {}", connection.hashCode());
  }

  public synchronized void batchUpdate() {
    ++this.nestedBatches;
  }

  public synchronized void completeUpdateBatch() {
    if (--this.nestedBatches == 0) flushUpdateBatch();
  }

  private void flushUpdateBatch() {
    if (pendingNotifications.isEmpty()) return;

    LOGGER.trace("Publishing batched changes");
    for (Map.Entry<HomekitClientConnection, List<PendingNotification>> entry :
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

  public synchronized void publish(int accessoryId, int iid, EventableCharacteristic changed) {
    final ConnectionsWithIds subscribers = subscriptions.get(changed);
    if (subscribers == null || subscribers.connections.isEmpty()) {
      LOGGER.trace("No subscribers to characteristic {} at accessory {} ", changed, accessoryId);
      return; // no subscribers
    }
    if (nestedBatches != 0) {
      LOGGER.trace("Batching change for accessory {} and characteristic {} " + accessoryId, iid);
      PendingNotification notification = new PendingNotification(accessoryId, iid, changed);
      for (HomekitClientConnection connection : subscribers.connections) {
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
      for (HomekitClientConnection connection : subscribers.connections) {
        connection.outOfBand(message);
      }
    } catch (Exception e) {
      LOGGER.warn("Failed to create new event message", e);
    }
  }

  /**
   * The accessory registry has changed; go through all subscriptions and link to any new/changed
   * characteristics
   */
  public synchronized void resync(HomekitRegistry registry) {
    LOGGER.trace("Resyncing subscriptions");
    flushUpdateBatch();

    Map<EventableCharacteristic, ConnectionsWithIds> newSubscriptions = new HashMap<>();
    Iterator<Map.Entry<EventableCharacteristic, ConnectionsWithIds>> i =
        subscriptions.entrySet().iterator();
    while (i.hasNext()) {
      Map.Entry<EventableCharacteristic, ConnectionsWithIds> entry = i.next();
      EventableCharacteristic oldCharacteristic = entry.getKey();
      ConnectionsWithIds subscribers = entry.getValue();
      Characteristic newCharacteristic =
          registry.getCharacteristics(subscribers.aid).get(subscribers.iid);
      if (newCharacteristic == null || newCharacteristic.getType() != oldCharacteristic.getType()) {
        // characteristic is gone or has completely changed; drop all subscriptions for it
        LOGGER.trace(
            "{}:{} ({}) has gone missing; dropping subscriptions.",
            subscribers.aid,
            subscribers.iid,
            oldCharacteristic.getClass().getSimpleName());
        i.remove();
        for (HomekitClientConnection conn : subscribers.connections) {
          removeSubscription(oldCharacteristic, conn);
        }
      } else if (newCharacteristic != oldCharacteristic) {
        EventableCharacteristic newEventableCharacteristic =
            (EventableCharacteristic) newCharacteristic;
        LOGGER.trace(
            "{}:{} has been replaced by a compatible characteristic; re-connecting subscriptions",
            subscribers.aid,
            subscribers.iid);
        // characteristic has been replaced by another instance of the same thing;
        // re-connect subscriptions
        i.remove();
        oldCharacteristic.unsubscribe();
        subscribe(subscribers.aid, subscribers.iid, newEventableCharacteristic);
        // we can't replace the key, and we can't add to the map while we're iterating,
        // so save it off to a temporary map that we'll add them all at the end
        newSubscriptions.put(newEventableCharacteristic, subscribers);

        for (HomekitClientConnection conn : subscribers.connections) {
          Set<EventableCharacteristic> subscribedCharacteristics = reverse.get(conn);
          subscribedCharacteristics.remove(oldCharacteristic);
          subscribedCharacteristics.add(newEventableCharacteristic);

          // and also update references for any pending notifications, so they'll get the proper
          // value
          List<PendingNotification> connectionPendingNotifications = pendingNotifications.get(conn);
          if (connectionPendingNotifications != null) {
            for (PendingNotification notification : connectionPendingNotifications) {
              if (notification.characteristic == oldCharacteristic) {
                notification.characteristic = newEventableCharacteristic;
              }
            }
          }
        }
      }
    }
    subscriptions.putAll(newSubscriptions);
  }

  private void subscribe(int aid, int iid, EventableCharacteristic characteristic) {
    characteristic.subscribe(
        () -> {
          publish(aid, iid, characteristic);
        });
  }

  /** Remove all existing subscriptions */
  public synchronized void removeAll() {
    LOGGER.trace("Removing {} reverse connections from subscription manager", reverse.size());
    Iterator<Map.Entry<HomekitClientConnection, Set<EventableCharacteristic>>> i =
        reverse.entrySet().iterator();
    while (i.hasNext()) {
      Map.Entry<HomekitClientConnection, Set<EventableCharacteristic>> entry = i.next();
      HomekitClientConnection connection = entry.getKey();
      LOGGER.trace("Removing connection {}", connection.hashCode());
      i.remove();
      removeConnection(connection, entry.getValue());
    }
    LOGGER.trace("Subscription sizes are {} and {}", reverse.size(), subscriptions.size());
  }
}
