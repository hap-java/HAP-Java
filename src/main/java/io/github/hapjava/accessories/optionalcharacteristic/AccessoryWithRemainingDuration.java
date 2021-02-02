package io.github.hapjava.accessories.optionalcharacteristic;

import io.github.hapjava.characteristics.HomekitCharacteristicChangeCallback;
import io.github.hapjava.characteristics.impl.valve.RemainingDurationCharacteristic;
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
   * return the min value for remaining duration. overwrite if you want to change the default value.
   *
   * @return min remaining duration
   */
  default int getMinRemainingDuration() {
    return RemainingDurationCharacteristic.DEFAULT_MIN_VALUE;
  }

  /**
   * return the max value for remaining duration. overwrite if you want to change the default value.
   *
   * @return max remaining duration
   */
  default int getMaxRemainingDuration() {
    return RemainingDurationCharacteristic.DEFAULT_MAX_VALUE;
  }

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
