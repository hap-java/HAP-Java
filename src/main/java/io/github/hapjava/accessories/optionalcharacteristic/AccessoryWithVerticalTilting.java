package io.github.hapjava.accessories.optionalcharacteristic;

import io.github.hapjava.characteristics.HomekitCharacteristicChangeCallback;
import java.util.concurrent.CompletableFuture;

/** Accessory with vertical tilting characteristic. */
public interface AccessoryWithVerticalTilting {
  /**
   * Retrieves the current vertical tilt angle
   *
   * @return a future that will contain the position as a value between -90 and 90
   */
  CompletableFuture<Integer> getCurrentVerticalTiltAngle();

  /**
   * Retrieves the target vertical tilt angle
   *
   * @return a future that will contain the target position as a value between -90 and 90
   */
  CompletableFuture<Integer> getTargetVerticalTiltAngle();

  /**
   * Sets the target position
   *
   * @param angle the target angle to set, as a value between -90 and 90
   * @return a future that completes when the change is made
   * @throws Exception when the change cannot be made
   */
  CompletableFuture<Void> setTargetVerticalTiltAngle(int angle) throws Exception;

  /**
   * Subscribes to changes in the current vertical tilt angle.
   *
   * @param callback the function to call when the state changes.
   */
  void subscribeCurrentVerticalTiltAngle(HomekitCharacteristicChangeCallback callback);

  /**
   * Subscribes to changes in the target vertical tilt angle.
   *
   * @param callback the function to call when the state changes.
   */
  void subscribeTargetVerticalTiltAngle(HomekitCharacteristicChangeCallback callback);

  /** Unsubscribes from changes in the current vertical tilt angle */
  void unsubscribeCurrentVerticalTiltAngle();

  /** Unsubscribes from changes in the target vertical tilt angle */
  void unsubscribeTargetVerticalTiltAngle();
}
