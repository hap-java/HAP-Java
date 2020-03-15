package io.github.hapjava.accessories.optionalcharacteristic;

import io.github.hapjava.characteristics.HomekitCharacteristicChangeCallback;
import io.github.hapjava.characteristics.impl.fan.CurrentFanStateEnum;
import io.github.hapjava.characteristics.impl.fan.LockPhysicalControlsEnum;
import io.github.hapjava.characteristics.impl.fan.RotationDirectionEnum;
import io.github.hapjava.characteristics.impl.fan.SwingModeEnum;
import io.github.hapjava.characteristics.impl.fan.TargetFanStateEnum;
import java.util.concurrent.CompletableFuture;

/**
 * A fan, with power and rotational characteristics.
 *
 * @author Andy Lintner
 */
public interface AccessoryWithFanState {

  /**
   * Retrieves the current state of the fan (INACTIVE, IDLE, BLOWING AIR).
   *
   * @return a future that will contain the state
   */
  CompletableFuture<CurrentFanStateEnum> getCurrentFanState();

  /**
   * Retrieves the target state of the fan (MANUAL, AUTO).
   *
   * @return a future that will contain the state
   */
  CompletableFuture<TargetFanStateEnum> getTargetFanState();

  /**
   * Set the target state of the fan (MANUAL, AUTO).
   *
   * @param targetState target state
   * @return a future that completes when the change is made
   */
  CompletableFuture<Void> setTargetFanState(TargetFanStateEnum targetState);

  /**
   * Retrieves the swing mode of the fan.
   *
   * @return a future that will contain the swing mode
   */
  CompletableFuture<SwingModeEnum> getSwingMode();

  /**
   * Set the swing mode of the fan (DISABLED, ENABLED).
   *
   * @param swingMode swing mode
   * @return a future that completes when the change is made
   */
  CompletableFuture<Void> setSwingMode(SwingModeEnum swingMode);

  /**
   * OPTIONAL: Retrieves the lock controls of the fan.
   *
   * @return a future that will contain the lock controls
   */
  CompletableFuture<LockPhysicalControlsEnum> getLockControls();

  /**
   * Set the lock controls of the fan (DISABLED, ENABLED).
   *
   * @param lockControls lock controls mode
   * @return a future that completes when the change is made
   */
  CompletableFuture<Void> setLockControls(LockPhysicalControlsEnum lockControls);

  /**
   * Retrieves the current rotation direction of the fan.
   *
   * @return a future that will contain the direction
   */
  CompletableFuture<RotationDirectionEnum> getRotationDirection();

  /**
   * Retrieves the current speed of the fan's rotation
   *
   * @return a future that will contain the speed, expressed as an integer between 0 and 100.
   */
  CompletableFuture<Integer> getRotationSpeed();

  /**
   * Sets the rotation direction of the fan
   *
   * @param direction the direction to set
   * @return a future that completes when the change is made
   * @throws Exception when the change cannot be made
   */
  CompletableFuture<Void> setRotationDirection(RotationDirectionEnum direction) throws Exception;

  /**
   * OPTIONAL: Sets the speed of the fan's rotation
   *
   * @param speed the speed to set, expressed as an integer between 0 and 100.
   * @return a future that completes when the change is made
   * @throws Exception when the change cannot be made
   */
  CompletableFuture<Void> setRotationSpeed(Integer speed) throws Exception;

  /**
   * Subscribes to changes in the rotation direction of the fan.
   *
   * @param callback the function to call when the direction changes.
   */
  void subscribeRotationDirection(HomekitCharacteristicChangeCallback callback);

  /**
   * Subscribes to changes in the current fan state.
   *
   * @param callback the function to call when the direction changes.
   */
  void subscribeCurrentFanState(HomekitCharacteristicChangeCallback callback);

  /**
   * Subscribes to changes in the target fan state.
   *
   * @param callback the function to call when the direction changes.
   */
  void subscribeTargetFanState(HomekitCharacteristicChangeCallback callback);

  /**
   * Subscribes to changes in the rotation speed of the fan.
   *
   * @param callback the function to call when the speed changes.
   */
  void subscribeRotationSpeed(HomekitCharacteristicChangeCallback callback);

  /**
   * Subscribes to changes in the swing mode of the fan.
   *
   * @param callback the function to call when the swing mode changes.
   */
  void subscribeSwingMode(HomekitCharacteristicChangeCallback callback);

  /**
   * Subscribes to changes in the lock controls of the fan.
   *
   * @param callback the function to call when the lock controls changes.
   */
  void subscribeLockControls(HomekitCharacteristicChangeCallback callback);

  /** Unsubscribes from changes in the rotation direction of the fan. */
  void unsubscribeRotationDirection();

  /** Unsubscribes from changes in the fan's rotation speed. */
  void unsubscribeRotationSpeed();

  /** Unsubscribes from changes in the current state of the fan. */
  void unsubscribeCurrentFanState();

  /** Unsubscribes from changes in the target state of the fan. */
  void unsubscribeTargetFanState();

  /** Unsubscribes from changes in the swing mode of the fan. */
  void unsubscribeSwingMode();

  /** Unsubscribes from changes in the lock controls of the fan. */
  void unsubscribeLockControls();
}
