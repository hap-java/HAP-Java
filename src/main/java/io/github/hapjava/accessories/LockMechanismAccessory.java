package io.github.hapjava.accessories;

import io.github.hapjava.characteristics.HomekitCharacteristicChangeCallback;
import io.github.hapjava.characteristics.impl.lock.LockCurrentStateEnum;
import io.github.hapjava.characteristics.impl.lock.LockTargetStateEnum;
import io.github.hapjava.services.Service;
import io.github.hapjava.services.impl.LockMechanismService;
import java.util.Collection;
import java.util.Collections;
import java.util.concurrent.CompletableFuture;

/** Lock mechanism with current and target state. */
public interface LockMechanismAccessory extends HomekitAccessory {

  /**
   * Retrieves the lock current state.
   *
   * @return a future that will contain the lock current state .
   */
  CompletableFuture<LockCurrentStateEnum> getLockCurrentState();

  /**
   * Retrieves the lock target state.
   *
   * @return a future that will contain the lock target state .
   */
  CompletableFuture<LockTargetStateEnum> getLockTargetState();

  /**
   * set lock target state the lock target state.
   *
   * @param state lock target state
   * @return a future that completes when the change is made
   */
  CompletableFuture<Void> setLockTargetState(LockTargetStateEnum state);

  /**
   * Subscribes to changes in the lock current state.
   *
   * @param callback the function to call when the state changes.
   */
  void subscribeLockCurrentState(HomekitCharacteristicChangeCallback callback);

  /** Unsubscribes from changes in lock current state. */
  void unsubscribeLockCurrentState();

  /**
   * Subscribes to changes in the lock target state.
   *
   * @param callback the function to call when the state changes.
   */
  void subscribeLockTargetState(HomekitCharacteristicChangeCallback callback);

  /** Unsubscribes from changes in lock target state. */
  void unsubscribeLockTargetState();

  @Override
  default Collection<Service> getServices() {
    return Collections.singleton(new LockMechanismService(this));
  }
}
