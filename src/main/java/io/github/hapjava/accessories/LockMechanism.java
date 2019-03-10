package io.github.hapjava.accessories;

import io.github.hapjava.HomekitAccessory;
import io.github.hapjava.HomekitCharacteristicChangeCallback;
import io.github.hapjava.Service;
import io.github.hapjava.accessories.properties.LockMechanismState;
import io.github.hapjava.impl.services.LockMechanismService;
import java.util.Collection;
import java.util.Collections;
import java.util.concurrent.CompletableFuture;

/**
 * A lock capable of exposing its binary locked state. For a lock that can be locked/unlocked, use
 * {@link LockableLockMechanism}.
 *
 * <p>Locks that run on batteries will need to implement this interface and also implement {@link
 * BatteryStatusAccessory}.
 *
 * @author Andy Lintner
 */
public interface LockMechanism extends HomekitAccessory {

  /**
   * Retrieves the current binary state of the lock.
   *
   * @return a future that will contain the binary state.
   */
  CompletableFuture<LockMechanismState> getCurrentMechanismState();

  /**
   * Subscribes to changes in the binary state of the lock.
   *
   * @param callback the function to call when the state changes.
   */
  void subscribeCurrentMechanismState(HomekitCharacteristicChangeCallback callback);

  /** Unsubscribes from changes in the binary state of the lock. */
  void unsubscribeCurrentMechanismState();

  @Override
  default Collection<Service> getServices() {
    return Collections.singleton(new LockMechanismService(this));
  }
}
