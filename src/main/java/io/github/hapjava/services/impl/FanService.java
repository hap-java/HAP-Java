package io.github.hapjava.services.impl;

import io.github.hapjava.accessories.FanAccessory;
import io.github.hapjava.accessories.optionalcharacteristic.AccessoryWithName;
import io.github.hapjava.characteristics.impl.common.ActiveCharacteristic;
import io.github.hapjava.characteristics.impl.common.ActiveEnum;
import io.github.hapjava.characteristics.impl.common.NameCharacteristic;
import io.github.hapjava.characteristics.impl.fan.CurrentFanStateCharacteristic;
import io.github.hapjava.characteristics.impl.fan.LockPhysicalControlsCharacteristic;
import io.github.hapjava.characteristics.impl.fan.RotationDirectionCharacteristic;
import io.github.hapjava.characteristics.impl.fan.RotationSpeedCharacteristic;
import io.github.hapjava.characteristics.impl.fan.SwingModeCharacteristic;
import io.github.hapjava.characteristics.impl.fan.TargetFanStateCharacteristic;

/** This service describes a fan. */
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
