package com.beowulfe.hap.accessories;

import com.beowulfe.hap.HomekitCharacteristicChangeCallback;
import com.beowulfe.hap.accessories.properties.LockMechanismState;
import java.util.concurrent.CompletableFuture;

/**
 * Extends {@link LockMechanism} with the ability to lock and unlock the mechanism.
 *
 * @author Andy Lintner
 */
public interface LockableLockMechanism extends LockMechanism {

  /**
   * Sets the binary state of the lock mechanism.
   *
   * @param state true for a locked mechanism, false for unlocked.
   * @throws Exception when the change cannot be made.
   */
  void setTargetMechanismState(LockMechanismState state) throws Exception;

  /**
   * Retrieves the pending, but not yet completed, state of the lock mechanism.
   *
   * @return the binary state
   */
  CompletableFuture<LockMechanismState> getTargetMechanismState();

  /**
   * Subscribes to changes in the pending, but not yet completed, binary state.
   *
   * @param callback the function to call when the state changes.
   */
  void subscribeTargetMechanismState(HomekitCharacteristicChangeCallback callback);

  /** Unsubscribes from changes in the pending, but not yet completed, binary state. */
  void unsubscribeTargetMechanismState();
}
