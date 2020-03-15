package io.github.hapjava.services.impl;

import io.github.hapjava.accessories.ThermostatAccessory;
import io.github.hapjava.accessories.optionalcharacteristic.AccessoryWithName;
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
            accessory::getCurrentState,
            accessory::subscribeCurrentState,
            accessory::unsubscribeCurrentState),
        new TargetHeatingCoolingStateCharacteristic(
            accessory::getTargetState,
            accessory::setTargetState,
            accessory::subscribeTargetState,
            accessory::unsubscribeTargetState),
        new CurrentTemperatureCharacteristic(
            accessory::getCurrentTemperature,
            accessory::subscribeCurrentTemperature,
            accessory::unsubscribeCurrentTemperature),
        new TargetTemperatureCharacteristic(
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
