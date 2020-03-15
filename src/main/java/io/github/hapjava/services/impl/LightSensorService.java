package io.github.hapjava.services.impl;

import io.github.hapjava.accessories.LightSensorAccessory;
import io.github.hapjava.characteristics.impl.accessoryinformation.NameCharacteristic;
import io.github.hapjava.characteristics.impl.battery.StatusLowBatteryCharacteristic;
import io.github.hapjava.characteristics.impl.common.StatusActiveCharacteristic;
import io.github.hapjava.characteristics.impl.common.StatusFaultCharacteristic;
import io.github.hapjava.characteristics.impl.common.StatusTamperedCharacteristic;
import io.github.hapjava.characteristics.impl.lightsensor.CurrentAmbientLightLevelCharacteristic;

/** This service describes a light sensor. */
public class LightSensorService extends AbstractServiceImpl {

  public LightSensorService(CurrentAmbientLightLevelCharacteristic lightLevel) {
    super("00000084-0000-1000-8000-0026BB765291");
    addCharacteristic(lightLevel);
  }

  public LightSensorService(LightSensorAccessory lightSensor) {
    this(
        new CurrentAmbientLightLevelCharacteristic(
            lightSensor::getCurrentAmbientLightLevel,
            lightSensor::subscribeCurrentAmbientLightLevel,
            lightSensor::unsubscribeCurrentAmbientLightLevel));
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
