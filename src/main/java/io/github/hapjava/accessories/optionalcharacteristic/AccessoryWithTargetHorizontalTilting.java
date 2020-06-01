package io.github.hapjava.accessories.optionalcharacteristic;

import io.github.hapjava.characteristics.HomekitCharacteristicChangeCallback;
import java.util.concurrent.CompletableFuture;

/**
 * Accessory with target horizontal tilting characteristic.
 *
 * @author Andy Lintner
 */
public interface AccessoryWithTargetHorizontalTilting {

  /**
   * Retrieves the target horizontal tilt angle
   *
   * @return a future that will contain the target position as a value between -90 and 90
   */
  CompletableFuture<Integer> getTargetHorizontalTiltAngle();

  /**
   * Sets the target position
   *
   * @param angle the target angle to set, as a value between -90 and 90
   * @return a future that completes when the change is made
   * @throws Exception when the change cannot be made
   */
  CompletableFuture<Void> setTargetHorizontalTiltAngle(int angle) throws Exception;

  /**
   * Subscribes to changes in the target horizontal tilt angle.
   *
   * @param callback the function to call when the state changes.
   */
  void subscribeTargetHorizontalTiltAngle(HomekitCharacteristicChangeCallback callback);

  /** Unsubscribes from changes in the target horizontal tilt angle */
  void unsubscribeTargetHorizontalTiltAngle();
}
