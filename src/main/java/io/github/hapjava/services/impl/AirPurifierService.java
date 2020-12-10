package io.github.hapjava.services.impl;

import io.github.hapjava.accessories.AirPurifierAccessory;
import io.github.hapjava.accessories.optionalcharacteristic.AccessoryWithName;
import io.github.hapjava.accessories.optionalcharacteristic.AccessoryWithPhysicalControlsLock;
import io.github.hapjava.accessories.optionalcharacteristic.AccessoryWithRotationSpeed;
import io.github.hapjava.accessories.optionalcharacteristic.AccessoryWithSwingMode;
import io.github.hapjava.characteristics.impl.airpurifier.CurrentAirPurifierCharacteristic;
import io.github.hapjava.characteristics.impl.airpurifier.TargetAirPurifierStateCharacteristic;
import io.github.hapjava.characteristics.impl.common.ActiveCharacteristic;
import io.github.hapjava.characteristics.impl.common.ActiveEnum;
import io.github.hapjava.characteristics.impl.common.NameCharacteristic;
import io.github.hapjava.characteristics.impl.fan.LockPhysicalControlsCharacteristic;
import io.github.hapjava.characteristics.impl.fan.RotationSpeedCharacteristic;
import io.github.hapjava.characteristics.impl.fan.SwingModeCharacteristic;

/** This service describes an air purifier. */
public class AirPurifierService extends AbstractServiceImpl {

  public AirPurifierService(
      ActiveCharacteristic active,
      CurrentAirPurifierCharacteristic currentState,
      TargetAirPurifierStateCharacteristic targetState) {
    super("000000BB-0000-1000-8000-0026BB765291");
    addCharacteristic(active);
    addCharacteristic(currentState);
    addCharacteristic(targetState);
  }

  public AirPurifierService(AirPurifierAccessory accessory) {
    this(
        new ActiveCharacteristic(
            () -> accessory.isActive().thenApply(s -> s ? ActiveEnum.ACTIVE : ActiveEnum.INACTIVE),
            (v) -> accessory.setActive(v == ActiveEnum.ACTIVE),
            accessory::subscribeActive,
            accessory::unsubscribeActive),
        new CurrentAirPurifierCharacteristic(
            accessory::getCurrentState,
            accessory::subscribeCurrentState,
            accessory::unsubscribeCurrentState),
        new TargetAirPurifierStateCharacteristic(
            accessory::getTargetState,
            accessory::setTargetState,
            accessory::subscribeCurrentState,
            accessory::unsubscribeCurrentState));
    if (accessory instanceof AccessoryWithName) {
      addOptionalCharacteristic(new NameCharacteristic(((AccessoryWithName) accessory)::getName));
    }
    if (accessory instanceof AccessoryWithSwingMode) {
      addOptionalCharacteristic(
          new SwingModeCharacteristic(
              ((AccessoryWithSwingMode) accessory)::getSwingMode,
              ((AccessoryWithSwingMode) accessory)::setSwingMode,
              ((AccessoryWithSwingMode) accessory)::subscribeSwingMode,
              ((AccessoryWithSwingMode) accessory)::unsubscribeSwingMode));
    }
    if (accessory instanceof AccessoryWithRotationSpeed) {
      addOptionalCharacteristic(
          new RotationSpeedCharacteristic(
              ((AccessoryWithRotationSpeed) accessory)::getRotationSpeed,
              ((AccessoryWithRotationSpeed) accessory)::setRotationSpeed,
              ((AccessoryWithRotationSpeed) accessory)::subscribeRotationSpeed,
              ((AccessoryWithRotationSpeed) accessory)::unsubscribeRotationSpeed));
    }
    if (accessory instanceof AccessoryWithPhysicalControlsLock) {
      addOptionalCharacteristic(
          new LockPhysicalControlsCharacteristic(
              ((AccessoryWithPhysicalControlsLock) accessory)::getLockControls,
              ((AccessoryWithPhysicalControlsLock) accessory)::setLockControls,
              ((AccessoryWithPhysicalControlsLock) accessory)::subscribeLockControls,
              ((AccessoryWithPhysicalControlsLock) accessory)::unsubscribeLockControls));
    }
  }

  public void addOptionalCharacteristic(NameCharacteristic name) {
    addCharacteristic(name);
  }

  public void addOptionalCharacteristic(SwingModeCharacteristic mode) {
    addCharacteristic(mode);
  }

  public void addOptionalCharacteristic(RotationSpeedCharacteristic speed) {
    addCharacteristic(speed);
  }

  public void addOptionalCharacteristic(LockPhysicalControlsCharacteristic lock) {
    addCharacteristic(lock);
  }
}
