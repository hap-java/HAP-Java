package io.github.hapjava.services.impl;

import io.github.hapjava.accessories.ThermostatAccessory;
import io.github.hapjava.characteristics.impl.accessoryinformation.NameCharacteristic;
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

  public ThermostatService(ThermostatAccessory thermostat) {
    this(
        new CurrentHeatingCoolingStateCharacteristic(
            thermostat::getCurrentState,
            thermostat::subscribeCurrentState,
            thermostat::unsubscribeCurrentState),
        new TargetHeatingCoolingStateCharacteristic(
            thermostat::getTargetState,
            thermostat::setTargetState,
            thermostat::subscribeTargetState,
            thermostat::unsubscribeTargetState),
        new CurrentTemperatureCharacteristic(
            thermostat::getCurrentTemperature,
            thermostat::subscribeCurrentTemperature,
            thermostat::unsubscribeCurrentTemperature),
        new TargetTemperatureCharacteristic(
            thermostat::getTargetTemperature,
            thermostat::setTargetTemperature,
            thermostat::subscribeTargetTemperature,
            thermostat::unsubscribeTargetTemperature),
        new TemperatureDisplayUnitCharacteristic(
            thermostat::getTemperatureDisplayUnit,
            thermostat::setTemperatureDisplayUnit,
            thermostat::subscribeTemperatureDisplayUnit,
            thermostat::unsubscribeTemperatureDisplayUnit));
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
