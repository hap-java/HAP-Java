package io.github.hapjava.services.impl;

import io.github.hapjava.accessories.ThermostatAccessory;
import io.github.hapjava.accessories.optionalcharacteristic.AccessoryWithCoolingThresholdTemperature;
import io.github.hapjava.accessories.optionalcharacteristic.AccessoryWithCurrentRelativeHumidity;
import io.github.hapjava.accessories.optionalcharacteristic.AccessoryWithHeatingThresholdTemperature;
import io.github.hapjava.accessories.optionalcharacteristic.AccessoryWithName;
import io.github.hapjava.accessories.optionalcharacteristic.AccessoryWithTargetRelativeHumidity;
import io.github.hapjava.characteristics.impl.common.NameCharacteristic;
import io.github.hapjava.characteristics.impl.humiditysensor.CurrentRelativeHumidityCharacteristic;
import io.github.hapjava.characteristics.impl.humiditysensor.TargetRelativeHumidityCharacteristic;
import io.github.hapjava.characteristics.impl.thermostat.CoolingThresholdTemperatureCharacteristic;
import io.github.hapjava.characteristics.impl.thermostat.CurrentHeatingCoolingStateCharacteristic;
import io.github.hapjava.characteristics.impl.thermostat.CurrentTemperatureCharacteristic;
import io.github.hapjava.characteristics.impl.thermostat.HeatingThresholdTemperatureCharacteristic;
import io.github.hapjava.characteristics.impl.thermostat.TargetHeatingCoolingStateCharacteristic;
import io.github.hapjava.characteristics.impl.thermostat.TargetTemperatureCharacteristic;
import io.github.hapjava.characteristics.impl.thermostat.TemperatureDisplayUnitCharacteristic;

/** This service describes a thermostat. */
public class ThermostatService extends AbstractServiceImpl {

  public ThermostatService(
      CurrentHeatingCoolingStateCharacteristic currentHeatingCoolingStateCharacteristic,
      TargetHeatingCoolingStateCharacteristic targetHeatingCoolingStateCharacteristic,
      CurrentTemperatureCharacteristic currentTemperatureCharacteristic,
      TargetTemperatureCharacteristic targetTemperatureCharacteristic,
      TemperatureDisplayUnitCharacteristic temperatureUnitsCharacteristic) {
    super("0000004A-0000-1000-8000-0026BB765291");
    addCharacteristic(currentHeatingCoolingStateCharacteristic);
    addCharacteristic(targetHeatingCoolingStateCharacteristic);
    addCharacteristic(currentTemperatureCharacteristic);
    addCharacteristic(targetTemperatureCharacteristic);
    addCharacteristic(temperatureUnitsCharacteristic);
  }

  public ThermostatService(ThermostatAccessory accessory) {
    this(
        new CurrentHeatingCoolingStateCharacteristic(
            accessory.getCurrentHeatingCoolingStateValidValues(),
            accessory::getCurrentState,
            accessory::subscribeCurrentState,
            accessory::unsubscribeCurrentState),
        new TargetHeatingCoolingStateCharacteristic(
            accessory.getTargetHeatingCoolingStateValidValues(),
            accessory::getTargetState,
            accessory::setTargetState,
            accessory::subscribeTargetState,
            accessory::unsubscribeTargetState),
        new CurrentTemperatureCharacteristic(
            accessory.getMinCurrentTemperature(),
            accessory.getMaxCurrentTemperature(),
            accessory.getMinStepCurrentTemperature(),
            accessory::getCurrentTemperature,
            accessory::subscribeCurrentTemperature,
            accessory::unsubscribeCurrentTemperature),
        new TargetTemperatureCharacteristic(
            accessory.getMinTargetTemperature(),
            accessory.getMaxTargetTemperature(),
            accessory.getMinStepTargetTemperature(),
            accessory::getTargetTemperature,
            accessory::setTargetTemperature,
            accessory::subscribeTargetTemperature,
            accessory::unsubscribeTargetTemperature),
        new TemperatureDisplayUnitCharacteristic(
            accessory::getTemperatureDisplayUnit,
            accessory::setTemperatureDisplayUnit,
            accessory::subscribeTemperatureDisplayUnit,
            accessory::unsubscribeTemperatureDisplayUnit));
    if (accessory instanceof AccessoryWithName) {
      addOptionalCharacteristic(new NameCharacteristic(((AccessoryWithName) accessory)::getName));
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
    if (accessory instanceof AccessoryWithCurrentRelativeHumidity) {
      addOptionalCharacteristic(
          new CurrentRelativeHumidityCharacteristic(
              ((AccessoryWithCurrentRelativeHumidity) accessory)::getCurrentRelativeHumidity,
              ((AccessoryWithCurrentRelativeHumidity) accessory)::subscribeCurrentRelativeHumidity,
              ((AccessoryWithCurrentRelativeHumidity) accessory)
                  ::unsubscribeCurrentRelativeHumidity));
    }
    if (accessory instanceof AccessoryWithTargetRelativeHumidity) {
      addOptionalCharacteristic(
          new TargetRelativeHumidityCharacteristic(
              ((AccessoryWithTargetRelativeHumidity) accessory)::getTargetRelativeHumidity,
              ((AccessoryWithTargetRelativeHumidity) accessory)::setTargetRelativeHumidity,
              ((AccessoryWithTargetRelativeHumidity) accessory)::subscribeTargetRelativeHumidity,
              ((AccessoryWithTargetRelativeHumidity) accessory)
                  ::unsubscribeTargetRelativeHumidity));
    }
  }

  public void addOptionalCharacteristic(NameCharacteristic name) {
    addCharacteristic(name);
  }

  public void addOptionalCharacteristic(CoolingThresholdTemperatureCharacteristic threshold) {
    addCharacteristic(threshold);
  }

  public void addOptionalCharacteristic(HeatingThresholdTemperatureCharacteristic threshold) {
    addCharacteristic(threshold);
  }

  public void addOptionalCharacteristic(CurrentRelativeHumidityCharacteristic humidity) {
    addCharacteristic(humidity);
  }

  public void addOptionalCharacteristic(TargetRelativeHumidityCharacteristic humidity) {
    addCharacteristic(humidity);
  }
}
