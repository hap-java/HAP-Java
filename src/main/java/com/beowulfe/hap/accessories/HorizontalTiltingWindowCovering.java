package com.beowulfe.hap.accessories;

import com.beowulfe.hap.HomekitCharacteristicChangeCallback;
import java.util.concurrent.CompletableFuture;

/**
 * Extends WindowCovering with the ability to control horizontal tilt angles
 *
 * @author Andy Lintner
 */
public interface HorizontalTiltingWindowCovering extends WindowCovering {

  /**
   * Retrieves the current horizontal tilt angle
   *
   * @return a future that will contain the position as a value between -90 and 90
   */
  CompletableFuture<Integer> getCurrentHorizontalTiltAngle();

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
   * Subscribes to changes in the current horizontal tilt angle.
   *
   * @param callback the function to call when the state changes.
   */
  void subscribeCurrentHorizontalTiltAngle(HomekitCharacteristicChangeCallback callback);

  /**
   * Subscribes to changes in the target horizontal tilt angle.
   *
   * @param callback the function to call when the state changes.
   */
  void subscribeTargetHorizontalTiltAngle(HomekitCharacteristicChangeCallback callback);

  /** Unsubscribes from changes in the current horizontal tilt angle */
  void unsubscribeCurrentHorizontalTiltAngle();

  /** Unsubscribes from changes in the target horizontal tilt angle */
  void unsubscribeTargetHorizontalTiltAngle();
}
