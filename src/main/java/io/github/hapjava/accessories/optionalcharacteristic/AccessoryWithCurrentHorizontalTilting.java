package io.github.hapjava.accessories.optionalcharacteristic;

import io.github.hapjava.characteristics.HomekitCharacteristicChangeCallback;
import java.util.concurrent.CompletableFuture;

/**
 * Accessory with current horizontal tilting characteristic.
 *
 * @author Andy Lintner
 */
public interface AccessoryWithCurrentHorizontalTilting {

  /**
   * Retrieves the current horizontal tilt angle
   *
   * @return a future that will contain the position as a value between -90 and 90
   */
  CompletableFuture<Integer> getCurrentHorizontalTiltAngle();

  /**
   * Subscribes to changes in the current horizontal tilt angle.
   *
   * @param callback the function to call when the state changes.
   */
  void subscribeCurrentHorizontalTiltAngle(HomekitCharacteristicChangeCallback callback);

  /** Unsubscribes from changes in the current horizontal tilt angle */
  void unsubscribeCurrentHorizontalTiltAngle();
}
