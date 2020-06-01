package io.github.hapjava.accessories.optionalcharacteristic;

import io.github.hapjava.characteristics.HomekitCharacteristicChangeCallback;
import io.github.hapjava.characteristics.impl.lock.LockTargetStateEnum;
import java.util.concurrent.CompletableFuture;

/** Accessory with lock target state. */
public interface AccessoryWithLockTargetState {

  /**
   * Retrieves the lock states. The target state of the physical security mechanism (e.g. deadbolt)
   *
   * @return a future with the value
   */
  CompletableFuture<LockTargetStateEnum> getLockTargetState();

  /**
   * Sets the lock target state
   *
   * @param state the target state to set
   * @return a future that completes when the change is made
   * @throws Exception when the change cannot be made
   */
  CompletableFuture<Void> setLockTargetState(LockTargetStateEnum state) throws Exception;

  /**
   * Subscribes to changes in lock target state.
   *
   * @param callback the function when the lock target state changes
   */
  void subscribeLockTargetState(HomekitCharacteristicChangeCallback callback);

  /** Unsubscribes from changes */
  void unsubscribeLockTargetState();
}
