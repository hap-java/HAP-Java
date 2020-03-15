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
   * Retrieves the current set duration for which the valve will be scheduled to run; this is
   * usually used as the duration to use when the valve is set to active.
   *
   * @return a future with the value
   */
  CompletableFuture<Integer> getSetDuration();

  /**
   * Sets the duration for which the valve will be scheduled to run; this is usually used as the
   * duration to use when the valve is set to active.
   *
   * <p>If the valve is currently running, then Homekit assumes that changing this value affects the
   * current remaining duration.
   *
   * @return a future with the value
   */
  CompletableFuture<Void> setSetDuration(int value);

  /**
   * Subscribes to changes in the set duration
   *
   * @param callback the function when the value has changed
   */
  void subscribeSetDuration(HomekitCharacteristicChangeCallback callback);

  /** Unsubscribes from changes */
  void unsubscribeSetDuration();
}
