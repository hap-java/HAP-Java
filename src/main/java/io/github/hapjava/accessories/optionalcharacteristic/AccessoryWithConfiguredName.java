package io.github.hapjava.accessories.optionalcharacteristic;

import io.github.hapjava.characteristics.HomekitCharacteristicChangeCallback;
import java.util.concurrent.CompletableFuture;

/** Accessory with configured name. */
public interface AccessoryWithConfiguredName {

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
}
