package io.github.hapjava.accessories.optionalcharacteristic;

import io.github.hapjava.characteristics.HomekitCharacteristicChangeCallback;
import java.util.concurrent.CompletableFuture;

/** Accessory with current tilting characteristic. */
public interface AccessoryWithCurrentTilting {

  /**
   * Retrieves the current tilt angle
   *
   * @return a future that will contain the position as a value between -90 and 90
   */
  CompletableFuture<Integer> getCurrentTiltAngle();

  /**
   * Subscribes to changes in the current tilt angle.
   *
   * @param callback the function to call when the state changes.
   */
  void subscribeCurrentTiltAngle(HomekitCharacteristicChangeCallback callback);

  /** Unsubscribes from changes in the current tilt angle */
  void unsubscribeCurrentTiltAngle();
}
