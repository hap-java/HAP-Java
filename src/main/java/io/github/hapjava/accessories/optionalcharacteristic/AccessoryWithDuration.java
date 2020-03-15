package io.github.hapjava.accessories.optionalcharacteristic;

import io.github.hapjava.characteristics.HomekitCharacteristicChangeCallback;
import java.util.concurrent.CompletableFuture;

/**
 * Accessory with duration characteristic.
 *
 * @author Eugen Freiter
 */
public interface AccessoryWithDuration {

  /**
   * Retrieves the current set duration;
   *
   * @return a future with the value
   */
  CompletableFuture<Integer> getSetDuration();

  /**
   * Sets the duration for which the service should run.
   *
   * @param value duration in seconds
   * @return a future that completes when the change is made
   */
  CompletableFuture<Void> setSetDuration(int value);

  /**
   * Subscribes to changes in the set duration
   *
   * @param callback the function when the duration has changed
   */
  void subscribeSetDuration(HomekitCharacteristicChangeCallback callback);

  /** Unsubscribes from changes */
  void unsubscribeSetDuration();
}
