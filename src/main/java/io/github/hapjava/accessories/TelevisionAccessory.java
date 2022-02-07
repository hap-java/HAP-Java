package io.github.hapjava.accessories;

import io.github.hapjava.characteristics.HomekitCharacteristicChangeCallback;
import io.github.hapjava.characteristics.impl.television.RemoteKeyEnum;
import io.github.hapjava.characteristics.impl.television.SleepDiscoveryModeEnum;
import io.github.hapjava.services.Service;
import io.github.hapjava.services.impl.TelevisionService;
import java.util.Collection;
import java.util.Collections;
import java.util.concurrent.CompletableFuture;

/** Television accessory. */
public interface TelevisionAccessory extends HomekitAccessory {

  /**
   * Retrieves the current active state of the TV.
   *
   * @return a future that will contain the state
   */
  CompletableFuture<Boolean> isActive();

  /**
   * Sets the active state of the TV
   *
   * @param state the state to set
   * @return a future that completes when the change is made
   * @throws Exception when the change cannot be made
   */
  CompletableFuture<Void> setActive(boolean state) throws Exception;

  /**
   * Subscribes to changes in the active state of the TV .
   *
   * @param callback the function to call when the active state changes.
   */
  void subscribeActive(HomekitCharacteristicChangeCallback callback);

  /** Unsubscribes from changes in the active state of the TV. */
  void unsubscribeActive();

  /**
   * Retrieves the active identifier
   *
   * @return a future that will contain the active identifier.
   */
  CompletableFuture<Integer> getActiveIdentifier();

  /**
   * Sets the active identifier
   *
   * @param identifier the active identifier
   * @return a future that completes when the active identifier is changed
   * @throws Exception when the active identifier cannot be set
   */
  CompletableFuture<Void> setActiveIdentifier(Integer identifier) throws Exception;

  /**
   * Subscribes to changes in the active identifier.
   *
   * @param callback the function to call when the active identifier changes.
   */
  void subscribeActiveIdentifier(HomekitCharacteristicChangeCallback callback);

  /** Unsubscribes from changes in the active identifier. */
  void unsubscribeActiveIdentifier();

  /**
   * Retrieves configured name.
   *
   * @return configured name
   */
  CompletableFuture<String> getConfiguredName();

  /**
   * Sets the configured name
   *
   * @param name configured name
   * @return a future that completes when the change is made
   * @throws Exception when the change cannot be made
   */
  CompletableFuture<Void> setConfiguredName(String name) throws Exception;

  /**
   * Subscribes to changes in configured name.
   *
   * @param callback the function to call when the configureed name changes.
   */
  void subscribeConfiguredName(HomekitCharacteristicChangeCallback callback);

  /** Unsubscribes from changes in the configured name state. */
  void unsubscribeConfiguredName();

  /**
   * Sends the remote key.
   *
   * @param key remote key
   * @return a future that completes when the change is made
   * @throws Exception when the change cannot be made
   */
  CompletableFuture<Void> setRemoteKey(RemoteKeyEnum key) throws Exception;

  /**
   * Retrieves the sleep discovery mode.
   *
   * @return a future that will contain the sleep discovery mode .
   */
  CompletableFuture<SleepDiscoveryModeEnum> getSleepDiscoveryMode();

  /**
   * Subscribes to changes in sleep discovery mode.
   *
   * @param callback the function to call when the sleep discovery mode changes.
   */
  void subscribeSleepDiscoveryMode(HomekitCharacteristicChangeCallback callback);

  /** Unsubscribes from changes in the sleep discovery mode. */
  void unsubscribeSleepDiscoveryMode();

  @Override
  default Collection<Service> getServices() {
    return Collections.singleton(new TelevisionService(this));
  }
}
