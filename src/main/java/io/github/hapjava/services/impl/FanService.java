package io.github.hapjava.services.impl;

import io.github.hapjava.accessories.FanAccessory;
import io.github.hapjava.accessories.optionalcharacteristic.AccessoryWithFanState;
import io.github.hapjava.accessories.optionalcharacteristic.AccessoryWithName;
import io.github.hapjava.accessories.optionalcharacteristic.AccessoryWithPhysicalControlsLock;
import io.github.hapjava.accessories.optionalcharacteristic.AccessoryWithRotationDirection;
import io.github.hapjava.accessories.optionalcharacteristic.AccessoryWithRotationSpeed;
import io.github.hapjava.accessories.optionalcharacteristic.AccessoryWithSwingMode;
import io.github.hapjava.characteristics.impl.common.ActiveCharacteristic;
import io.github.hapjava.characteristics.impl.common.ActiveEnum;
import io.github.hapjava.characteristics.impl.common.NameCharacteristic;
import io.github.hapjava.characteristics.impl.fan.CurrentFanStateCharacteristic;
import io.github.hapjava.characteristics.impl.fan.LockPhysicalControlsCharacteristic;
import io.github.hapjava.characteristics.impl.fan.RotationDirectionCharacteristic;
import io.github.hapjava.characteristics.impl.fan.RotationSpeedCharacteristic;
import io.github.hapjava.characteristics.impl.fan.SwingModeCharacteristic;
import io.github.hapjava.characteristics.impl.fan.TargetFanStateCharacteristic;

/**
 * This service describes a fan.
 *
 * <p>In the R1 release of the HAP specification, this is described as Fan v2. In the R1 release of
 * the HAP specification, this is simply described as Fan.
 */
public class FanService extends AbstractServiceImpl {

  public FanService(ActiveCharacteristic active) {
    super("000000B7-0000-1000-8000-0026BB765291");
    addCharacteristic(active);
  }

  public FanService(FanAccessory accessory) {
    this(
        new ActiveCharacteristic(
            () -> accessory.isActive().thenApply(s -> s ? ActiveEnum.ACTIVE : ActiveEnum.INACTIVE),
            (v) -> accessory.setActive(v == ActiveEnum.ACTIVE),
            accessory::subscribeActive,
            accessory::unsubscribeActive));
    if (accessory instanceof AccessoryWithName) {
      addOptionalCharacteristic(new NameCharacteristic(((AccessoryWithName) accessory)::getName));
    }

    if (accessory instanceof AccessoryWithFanState) {
      addOptionalCharacteristic(
          new CurrentFanStateCharacteristic(
              ((AccessoryWithFanState) accessory)::getCurrentFanState,
              ((AccessoryWithFanState) accessory)::subscribeCurrentFanState,
              ((AccessoryWithFanState) accessory)::unsubscribeCurrentFanState));
      addOptionalCharacteristic(
          new TargetFanStateCharacteristic(
              ((AccessoryWithFanState) accessory)::getTargetFanState,
              ((AccessoryWithFanState) accessory)::setTargetFanState,
              ((AccessoryWithFanState) accessory)::subscribeTargetFanState,
              ((AccessoryWithFanState) accessory)::unsubscribeTargetFanState));
    }
    if (accessory instanceof AccessoryWithRotationDirection) {
      addOptionalCharacteristic(
          new RotationDirectionCharacteristic(
              ((AccessoryWithRotationDirection) accessory)::getRotationDirection,
              ((AccessoryWithRotationDirection) accessory)::setRotationDirection,
              ((AccessoryWithRotationDirection) accessory)::subscribeRotationDirection,
              ((AccessoryWithRotationDirection) accessory)::unsubscribeRotationDirection));
    }
    if (accessory instanceof AccessoryWithRotationSpeed) {
      addOptionalCharacteristic(
          new RotationSpeedCharacteristic(
              ((AccessoryWithRotationSpeed) accessory)::getRotationSpeed,
              ((AccessoryWithRotationSpeed) accessory)::setRotationSpeed,
              ((AccessoryWithRotationSpeed) accessory)::subscribeRotationSpeed,
              ((AccessoryWithRotationSpeed) accessory)::unsubscribeRotationSpeed));
    }
    if (accessory instanceof AccessoryWithSwingMode) {
      addOptionalCharacteristic(
          new SwingModeCharacteristic(
              ((AccessoryWithSwingMode) accessory)::getSwingMode,
              ((AccessoryWithSwingMode) accessory)::setSwingMode,
              ((AccessoryWithSwingMode) accessory)::subscribeSwingMode,
              ((AccessoryWithSwingMode) accessory)::unsubscribeSwingMode));
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

  public void addOptionalCharacteristic(CurrentFanStateCharacteristic state) {
    addCharacteristic(state);
  }

  public void addOptionalCharacteristic(TargetFanStateCharacteristic state) {
    addCharacteristic(state);
  }

  public void addOptionalCharacteristic(RotationDirectionCharacteristic direction) {
    addCharacteristic(direction);
  }

  public void addOptionalCharacteristic(RotationSpeedCharacteristic speed) {
    addCharacteristic(speed);
  }

  public void addOptionalCharacteristic(SwingModeCharacteristic mode) {
    addCharacteristic(mode);
  }

  public void addOptionalCharacteristic(LockPhysicalControlsCharacteristic lock) {
    addCharacteristic(lock);
  }
}
