package io.github.hapjava.services.impl;

import io.github.hapjava.accessories.HeaterCoolerAccessory;
import io.github.hapjava.accessories.optionalcharacteristic.AccessoryWithCoolingThresholdTemperature;
import io.github.hapjava.accessories.optionalcharacteristic.AccessoryWithHeatingThresholdTemperature;
import io.github.hapjava.accessories.optionalcharacteristic.AccessoryWithName;
import io.github.hapjava.accessories.optionalcharacteristic.AccessoryWithPhysicalControlsLock;
import io.github.hapjava.accessories.optionalcharacteristic.AccessoryWithRotationSpeed;
import io.github.hapjava.accessories.optionalcharacteristic.AccessoryWithSwingMode;
import io.github.hapjava.accessories.optionalcharacteristic.AccessoryWithTemperatureDisplayUnits;
import io.github.hapjava.characteristics.impl.common.ActiveCharacteristic;
import io.github.hapjava.characteristics.impl.common.ActiveEnum;
import io.github.hapjava.characteristics.impl.common.NameCharacteristic;
import io.github.hapjava.characteristics.impl.fan.LockPhysicalControlsCharacteristic;
import io.github.hapjava.characteristics.impl.fan.RotationSpeedCharacteristic;
import io.github.hapjava.characteristics.impl.fan.SwingModeCharacteristic;
import io.github.hapjava.characteristics.impl.heatercooler.CurrentHeaterCoolerStateCharacteristic;
import io.github.hapjava.characteristics.impl.heatercooler.TargetHeaterCoolerStateCharacteristic;
import io.github.hapjava.characteristics.impl.thermostat.CoolingThresholdTemperatureCharacteristic;
import io.github.hapjava.characteristics.impl.thermostat.CurrentTemperatureCharacteristic;
import io.github.hapjava.characteristics.impl.thermostat.HeatingThresholdTemperatureCharacteristic;
import io.github.hapjava.characteristics.impl.thermostat.TemperatureDisplayUnitCharacteristic;

/** This service can be used to describe a heater and/or a cooler */
public class HeaterCoolerService extends AbstractServiceImpl {

  public HeaterCoolerService(
      ActiveCharacteristic activeCharacteristic,
      CurrentTemperatureCharacteristic currentTemperatureCharacteristic,
      CurrentHeaterCoolerStateCharacteristic currentHeaterCoolerStateCharacteristic,
      TargetHeaterCoolerStateCharacteristic targetHeaterCoolerStateCharacteristic) {
    super("000000BC-0000-1000-8000-0026BB765291");
    addCharacteristic(activeCharacteristic);
    addCharacteristic(currentTemperatureCharacteristic);
    addCharacteristic(currentHeaterCoolerStateCharacteristic);
    addCharacteristic(targetHeaterCoolerStateCharacteristic);
  }

  public HeaterCoolerService(HeaterCoolerAccessory accessory) {
    this(
        new ActiveCharacteristic(
            () -> accessory.isActive().thenApply(s -> s ? ActiveEnum.ACTIVE : ActiveEnum.INACTIVE),
            (v) -> accessory.setActive(v == ActiveEnum.ACTIVE),
            accessory::subscribeActive,
            accessory::unsubscribeActive),
        new CurrentTemperatureCharacteristic(
            accessory::getCurrentTemperature,
            accessory::subscribeCurrentTemperature,
            accessory::unsubscribeCurrentTemperature),
        new CurrentHeaterCoolerStateCharacteristic(
            accessory.getCurrentHeaterCoolerStateValidValues(),
            accessory::getCurrentHeaterCoolerState,
            accessory::subscribeCurrentHeaterCoolerState,
            accessory::unsubscribeCurrentHeaterCoolerState),
        new TargetHeaterCoolerStateCharacteristic(
            accessory.getTargetHeaterCoolerStateValidValues(),
            accessory::getTargetHeaterCoolerState,
            accessory::setTargetHeaterCoolerState,
            accessory::subscribeTargetHeaterCoolerState,
            accessory::unsubscribeTargetHeaterCoolerState));
    if (accessory instanceof AccessoryWithName) {
      addOptionalCharacteristic(new NameCharacteristic(((AccessoryWithName) accessory)::getName));
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
    if (accessory instanceof AccessoryWithCoolingThresholdTemperature) {
      addOptionalCharacteristic(
          new CoolingThresholdTemperatureCharacteristic(
              ((AccessoryWithCoolingThresholdTemperature) accessory)
                  .getMinCoolingThresholdTemperature(),
              ((AccessoryWithCoolingThresholdTemperature) accessory)
                  .getMaxCoolingThresholdTemperature(),
              ((AccessoryWithCoolingThresholdTemperature) accessory)
                  .getStepCoolingThresholdTemperature(),
              ((AccessoryWithCoolingThresholdTemperature) accessory)
                  ::getCoolingThresholdTemperature,
              ((AccessoryWithCoolingThresholdTemperature) accessory)
                  ::setCoolingThresholdTemperature,
              ((AccessoryWithCoolingThresholdTemperature) accessory)
                  ::subscribeCoolingThresholdTemperature,
              ((AccessoryWithCoolingThresholdTemperature) accessory)
                  ::unsubscribeCoolingThresholdTemperature));
    }
    if (accessory instanceof AccessoryWithHeatingThresholdTemperature) {
      addOptionalCharacteristic(
          new HeatingThresholdTemperatureCharacteristic(
              ((AccessoryWithHeatingThresholdTemperature) accessory)
                  .getMinHeatingThresholdTemperature(),
              ((AccessoryWithHeatingThresholdTemperature) accessory)
                  .getMaxHeatingThresholdTemperature(),
              ((AccessoryWithHeatingThresholdTemperature) accessory)
                  .getStepHeatingThresholdTemperature(),
              ((AccessoryWithHeatingThresholdTemperature) accessory)
                  ::getHeatingThresholdTemperature,
              ((AccessoryWithHeatingThresholdTemperature) accessory)
                  ::setHeatingThresholdTemperature,
              ((AccessoryWithHeatingThresholdTemperature) accessory)
                  ::subscribeHeatingThresholdTemperature,
              ((AccessoryWithHeatingThresholdTemperature) accessory)
                  ::unsubscribeHeatingThresholdTemperature));
    }
    if (accessory instanceof AccessoryWithTemperatureDisplayUnits) {
      addOptionalCharacteristic(
          new TemperatureDisplayUnitCharacteristic(
              ((AccessoryWithTemperatureDisplayUnits) accessory)::getTemperatureDisplayUnits,
              ((AccessoryWithTemperatureDisplayUnits) accessory)::setTemperatureDisplayUnits,
              ((AccessoryWithTemperatureDisplayUnits) accessory)::subscribeTemperatureDisplayUnits,
              ((AccessoryWithTemperatureDisplayUnits) accessory)
                  ::unsubscribeTemperatureDisplayUnits));
    }
  }

  public void addOptionalCharacteristic(NameCharacteristic name) {
    addCharacteristic(name);
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

  public void addOptionalCharacteristic(CoolingThresholdTemperatureCharacteristic threshold) {
    addCharacteristic(threshold);
  }

  public void addOptionalCharacteristic(HeatingThresholdTemperatureCharacteristic threshold) {
    addCharacteristic(threshold);
  }

  public void addOptionalCharacteristic(TemperatureDisplayUnitCharacteristic displayUnits) {
    addCharacteristic(displayUnits);
  }
}
