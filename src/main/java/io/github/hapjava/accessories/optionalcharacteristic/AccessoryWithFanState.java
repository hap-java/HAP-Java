package io.github.hapjava.accessories.optionalcharacteristic;

import io.github.hapjava.characteristics.HomekitCharacteristicChangeCallback;
import io.github.hapjava.characteristics.impl.fan.CurrentFanStateEnum;
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

  /** Unsubscribes from changes in the current state of the fan. */
  void unsubscribeCurrentFanState();

  /** Unsubscribes from changes in the target state of the fan. */
  void unsubscribeTargetFanState();
}
