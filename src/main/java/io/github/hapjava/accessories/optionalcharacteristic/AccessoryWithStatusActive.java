package io.github.hapjava.accessories.optionalcharacteristic;

import io.github.hapjava.characteristics.HomekitCharacteristicChangeCallback;
import java.util.concurrent.CompletableFuture;

/**
 * Accessory with status active.
 *
 * @author Eugen Freiter
 */
public interface AccessoryWithStatusActive {

  /**
   * Retrieves the status active. A value of true indicates that the accessory is active and is
   * functioning without any errors.
   *
   * @return a future with the value
   */
  CompletableFuture<Boolean> getStatusActive();

  /**
   * Subscribes to changes in status active.
   *
   * @param callback the function when the status active changes
   */
  void subscribeStatusActive(HomekitCharacteristicChangeCallback callback);

  /** Unsubscribes from changes */
  void unsubscribeStatusActive();
}
