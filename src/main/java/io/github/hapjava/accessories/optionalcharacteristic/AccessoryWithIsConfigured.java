package io.github.hapjava.accessories.optionalcharacteristic;

import io.github.hapjava.characteristics.HomekitCharacteristicChangeCallback;
import io.github.hapjava.characteristics.impl.common.IsConfiguredEnum;
import java.util.concurrent.CompletableFuture;

/**
 * Accessory with isConfigured characteristics. This characteristic describes if the service is
 * configured for use. For example, all of the valves in an irrigation system may not be configured
 * depending on physical wire connection.
 *
 * @author Eugen Freiter
 */
public interface AccessoryWithIsConfigured {

  /**
   * isConfigured state
   *
   * @return a future that will contain the isConfigured state
   */
  CompletableFuture<IsConfiguredEnum> getIsConfigured();

  /**
   * Set the isConfigured (NOT_CONFIGURED, CONFIGURED).
   *
   * @param isConfigured isConfigured state
   * @return a future that completes when the change is made
   */
  CompletableFuture<Void> setIsConfigured(IsConfiguredEnum isConfigured);

  /**
   * Subscribes to changes in isConfigured state
   *
   * @param callback the function to call when the isConfigured state changes.
   */
  void subscribeIsConfigured(HomekitCharacteristicChangeCallback callback);

  /** Unsubscribes from changes in the isConfigured state. */
  void unsubscribeIsConfigured();
}
