package io.github.hapjava.services.impl;

import io.github.hapjava.accessories.TemperatureSensorAccessory;
import io.github.hapjava.characteristics.impl.accessoryinformation.NameCharacteristic;
import io.github.hapjava.characteristics.impl.battery.StatusLowBatteryCharacteristic;
import io.github.hapjava.characteristics.impl.common.StatusActiveCharacteristic;
import io.github.hapjava.characteristics.impl.common.StatusFaultCharacteristic;
import io.github.hapjava.characteristics.impl.common.StatusTamperedCharacteristic;
import io.github.hapjava.characteristics.impl.thermostat.CurrentTemperatureCharacteristic;

/** This service describes a Temperature Sensor. */
public class TemperatureSensorService extends AbstractServiceImpl {

  public TemperatureSensorService(
      CurrentTemperatureCharacteristic currentTemperatureCharacteristic) {
    super("0000008A-0000-1000-8000-0026BB765291");
    addCharacteristic(currentTemperatureCharacteristic);
  }

  public TemperatureSensorService(TemperatureSensorAccessory temperatureSensor) {
    this(
        new CurrentTemperatureCharacteristic(
            temperatureSensor::getCurrentTemperature,
            temperatureSensor::subscribeCurrentTemperature,
            temperatureSensor::unsubscribeCurrentTemperature));
  }

  public void addOptionalCharacteristic(NameCharacteristic name) {
    addCharacteristic(name);
  }

  public void addOptionalCharacteristic(StatusActiveCharacteristic statusActive) {
    addCharacteristic(statusActive);
  }

  public void addOptionalCharacteristic(StatusFaultCharacteristic statusFault) {
    addCharacteristic(statusFault);
  }

  public void addOptionalCharacteristic(StatusTamperedCharacteristic statusTampered) {
    addCharacteristic(statusTampered);
  }

  public void addOptionalCharacteristic(StatusLowBatteryCharacteristic statusLowBattery) {
    addCharacteristic(statusLowBattery);
  }
}
