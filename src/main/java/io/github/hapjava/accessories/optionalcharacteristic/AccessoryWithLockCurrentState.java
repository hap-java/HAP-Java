package io.github.hapjava.accessories.optionalcharacteristic;

import io.github.hapjava.characteristics.HomekitCharacteristicChangeCallback;
import io.github.hapjava.characteristics.impl.lock.LockCurrentStateEnum;
import java.util.concurrent.CompletableFuture;

/**
 * Accessory with current lock state.
 *
 * @author Eugen Freiter
 */
public interface AccessoryWithLockCurrentState {

  /**
   * Retrieves the lock states. The current state of the physical security mechanism (e.g. deadbolt)
   *
   * @return a future with the value
   */
  CompletableFuture<LockCurrentStateEnum> getLockCurrentState();

  /**
   * Subscribes to changes in lock current state.
   *
   * @param callback the function when the lock current state changes
   */
  void subscribeLockCurrentState(HomekitCharacteristicChangeCallback callback);

  /** Unsubscribes from changes */
  void unsubscribeLockCurrentState();
}
