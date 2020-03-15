package io.github.hapjava.services.impl;

import io.github.hapjava.accessories.LockMechanismAccessory;
import io.github.hapjava.characteristics.impl.accessoryinformation.NameCharacteristic;
import io.github.hapjava.characteristics.impl.lock.LockCurrentStateCharacteristic;
import io.github.hapjava.characteristics.impl.lock.LockTargetStateCharacteristic;

/**
 * The HomeKit Lock Mechanism Service is designed to expose and control the physical lock mechanism
 * on a device.
 */
public class LockMechanismService extends AbstractServiceImpl {

  public LockMechanismService(
      LockCurrentStateCharacteristic lockCurrentState,
      LockTargetStateCharacteristic lockTargetState) {
    super("00000045-0000-1000-8000-0026BB765291");
    addCharacteristic(lockCurrentState);
    addCharacteristic(lockTargetState);
  }

  public LockMechanismService(LockMechanismAccessory lockMechanism) {
    this(
        new LockCurrentStateCharacteristic(
            lockMechanism::getLockCurrentState,
            lockMechanism::subscribeLockCurrentState,
            lockMechanism::unsubscribeLockCurrentState),
        new LockTargetStateCharacteristic(
            lockMechanism::getLockTargetState,
            lockMechanism::setLockTargetState,
            lockMechanism::subscribeLockTargetState,
            lockMechanism::unsubscribeLockTargetState));
  }

  public void addOptionalCharacteristic(NameCharacteristic name) {
    addCharacteristic(name);
  }
}
