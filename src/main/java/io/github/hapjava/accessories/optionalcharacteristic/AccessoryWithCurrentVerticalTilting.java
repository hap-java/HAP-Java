package io.github.hapjava.accessories.optionalcharacteristic;

import io.github.hapjava.characteristics.HomekitCharacteristicChangeCallback;
import java.util.concurrent.CompletableFuture;

/** Accessory with current vertical tilting characteristic. */
public interface AccessoryWithCurrentVerticalTilting {
  /**
   * Retrieves the current vertical tilt angle
   *
   * @return a future that will contain the position as a value between -90 and 90
   */
  CompletableFuture<Integer> getCurrentVerticalTiltAngle();

  /**
   * Subscribes to changes in the current vertical tilt angle.
   *
   * @param callback the function to call when the state changes.
   */
  void subscribeCurrentVerticalTiltAngle(HomekitCharacteristicChangeCallback callback);

  /** Unsubscribes from changes in the current vertical tilt angle */
  void unsubscribeCurrentVerticalTiltAngle();
}
