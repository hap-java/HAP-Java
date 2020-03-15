package io.github.hapjava.services.impl;

import io.github.hapjava.accessories.SmokeSensorAccessory;
import io.github.hapjava.characteristics.impl.accessoryinformation.NameCharacteristic;
import io.github.hapjava.characteristics.impl.battery.StatusLowBatteryCharacteristic;
import io.github.hapjava.characteristics.impl.common.StatusActiveCharacteristic;
import io.github.hapjava.characteristics.impl.common.StatusFaultCharacteristic;
import io.github.hapjava.characteristics.impl.common.StatusTamperedCharacteristic;
import io.github.hapjava.characteristics.impl.smokesensor.SmokeDetectedCharacteristic;

/** This service describes a Smoke detector Sensor. */
public class SmokeSensorService extends AbstractServiceImpl {

  public SmokeSensorService(SmokeDetectedCharacteristic smokeDetectedCharacteristic) {
    super("00000087-0000-1000-8000-0026BB765291");
    addCharacteristic(smokeDetectedCharacteristic);
  }

  public SmokeSensorService(SmokeSensorAccessory smokeSensor) {
    this(
        new SmokeDetectedCharacteristic(
            smokeSensor::getSmokeDetectedState,
            smokeSensor::subscribeSmokeDetectedState,
            smokeSensor::unsubscribeSmokeDetectedState));
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
