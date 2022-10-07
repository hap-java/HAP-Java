package io.github.hapjava.services.impl;

import io.github.hapjava.accessories.BasicFanAccessory;
import io.github.hapjava.accessories.optionalcharacteristic.AccessoryWithName;
import io.github.hapjava.accessories.optionalcharacteristic.AccessoryWithRotationDirection;
import io.github.hapjava.accessories.optionalcharacteristic.AccessoryWithRotationSpeed;
import io.github.hapjava.characteristics.impl.common.NameCharacteristic;
import io.github.hapjava.characteristics.impl.common.OnCharacteristic;
import io.github.hapjava.characteristics.impl.fan.RotationDirectionCharacteristic;
import io.github.hapjava.characteristics.impl.fan.RotationSpeedCharacteristic;

/**
 * This service describes a fan.
 *
 * <p>In the R1 release of the HAP specification, this is simply described as Fan. It is no longer
 * present in the R2 release.
 */
public class BasicFanService extends AbstractServiceImpl {

  public BasicFanService(OnCharacteristic on) {
    super("00000040-0000-1000-8000-0026BB765291");
    addCharacteristic(on);
  }

  public BasicFanService(BasicFanAccessory accessory) {
    this(
        new OnCharacteristic(
            () -> accessory.isOn(),
            (v) -> accessory.setOn(v),
            accessory::subscribeOn,
            accessory::unsubscribeOn));
    if (accessory instanceof AccessoryWithName) {
      addOptionalCharacteristic(new NameCharacteristic(((AccessoryWithName) accessory)::getName));
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
  }

  public void addOptionalCharacteristic(NameCharacteristic name) {
    addCharacteristic(name);
  }

  public void addOptionalCharacteristic(RotationDirectionCharacteristic direction) {
    addCharacteristic(direction);
  }

  public void addOptionalCharacteristic(RotationSpeedCharacteristic speed) {
    addCharacteristic(speed);
  }
}
