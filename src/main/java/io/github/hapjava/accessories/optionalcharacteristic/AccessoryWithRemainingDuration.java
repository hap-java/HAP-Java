package io.github.hapjava.accessories.optionalcharacteristic;

import io.github.hapjava.characteristics.HomekitCharacteristicChangeCallback;
import java.util.concurrent.CompletableFuture;

/**
 * Accessory with remaining duration characteristic.
 *
 * @author Tim Harper
 */
public interface AccessoryWithRemainingDuration {

  /**
   * Retrieves the remaining duration
   *
   * @return a future with the duration in seconds
   */
  CompletableFuture<Integer> getRemainingDuration();

  /**
   * Subscribes to changes in the duration; note it is not necessary to emit a change every second,
   * homekit infers the countdown progress client side.
   *
   * @param callback the function when the existing duration has been replaced with a new one.
   */
  void subscribeRemainingDuration(HomekitCharacteristicChangeCallback callback);

  /** Unsubscribes from changes */
  void unsubscribeRemainingDuration();
}
