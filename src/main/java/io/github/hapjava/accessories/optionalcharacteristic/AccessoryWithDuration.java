package io.github.hapjava.accessories.optionalcharacteristic;

import io.github.hapjava.characteristics.HomekitCharacteristicChangeCallback;
import io.github.hapjava.characteristics.impl.valve.SetDurationCharacteristic;
import java.util.concurrent.CompletableFuture;

/** Accessory with duration characteristic. */
public interface AccessoryWithDuration {

  /**
   * Retrieves the current set duration;
   *
   * @return a future with the value
   */
  CompletableFuture<Integer> getSetDuration();

  /**
   * return the min value for duration. overwrite if you want to change the default value.
   *
   * @return min remaining duration
   */
  default int getMinDuration() {
    return SetDurationCharacteristic.DEFAULT_MIN_VALUE;
  }

  /**
   * return the max value for duration. overwrite if you want to change the default value.
   *
   * @return max duration
   */
  default int getMaxDuration() {
    return SetDurationCharacteristic.DEFAULT_MAX_VALUE;
  }

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
