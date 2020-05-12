package io.github.hapjava.accessories.optionalcharacteristic;

import io.github.hapjava.characteristics.HomekitCharacteristicChangeCallback;
import io.github.hapjava.characteristics.impl.fan.LockPhysicalControlsEnum;
import java.util.concurrent.CompletableFuture;

/** Accessory with physical controls which can be locked, e.g. child lock */
public interface AccessoryWithPhysicalControlsLock {

  /**
   * OPTIONAL: Retrieves the lock controls.
   *
   * @return a future that will contain the lock controls
   */
  CompletableFuture<LockPhysicalControlsEnum> getLockControls();

  /**
   * Set the lock controls (DISABLED, ENABLED).
   *
   * @param lockControls lock controls mode
   * @return a future that completes when the change is made
   */
  CompletableFuture<Void> setLockControls(LockPhysicalControlsEnum lockControls);

  /**
   * Subscribes to changes in the lock controls.
   *
   * @param callback the function to call when the lock controls changes.
   */
  void subscribeLockControls(HomekitCharacteristicChangeCallback callback);

  /** Unsubscribes from changes in the lock controls. */
  void unsubscribeLockControls();
}
