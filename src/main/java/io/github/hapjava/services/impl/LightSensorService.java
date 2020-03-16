package io.github.hapjava.services.impl;

import io.github.hapjava.accessories.LightSensorAccessory;
import io.github.hapjava.accessories.optionalcharacteristic.AccessoryWithName;
import io.github.hapjava.accessories.optionalcharacteristic.AccessoryWithStatusActive;
import io.github.hapjava.accessories.optionalcharacteristic.AccessoryWithStatusFault;
import io.github.hapjava.accessories.optionalcharacteristic.AccessoryWithStatusLowBattery;
import io.github.hapjava.accessories.optionalcharacteristic.AccessoryWithStatusTampered;
import io.github.hapjava.characteristics.impl.battery.StatusLowBatteryCharacteristic;
import io.github.hapjava.characteristics.impl.common.NameCharacteristic;
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

  public LightSensorService(LightSensorAccessory accessory) {
    this(
        new CurrentAmbientLightLevelCharacteristic(
            accessory::getCurrentAmbientLightLevel,
            accessory::subscribeCurrentAmbientLightLevel,
            accessory::unsubscribeCurrentAmbientLightLevel));

    if (accessory instanceof AccessoryWithName) {
      addOptionalCharacteristic(new NameCharacteristic(((AccessoryWithName) accessory)::getName));
    }
    if (accessory instanceof AccessoryWithStatusActive) {
      addOptionalCharacteristic(
          new StatusActiveCharacteristic(
              ((AccessoryWithStatusActive) accessory)::getStatusActive,
              ((AccessoryWithStatusActive) accessory)::subscribeStatusActive,
              ((AccessoryWithStatusActive) accessory)::unsubscribeStatusActive));
    }
    if (accessory instanceof AccessoryWithStatusFault) {
      addOptionalCharacteristic(
          new StatusFaultCharacteristic(
              ((AccessoryWithStatusFault) accessory)::getStatusFault,
              ((AccessoryWithStatusFault) accessory)::subscribeStatusFault,
              ((AccessoryWithStatusFault) accessory)::unsubscribeStatusFault));
    }
    if (accessory instanceof AccessoryWithStatusTampered) {
      addOptionalCharacteristic(
          new StatusTamperedCharacteristic(
              ((AccessoryWithStatusTampered) accessory)::getStatusTampered,
              ((AccessoryWithStatusTampered) accessory)::subscribeStatusTampered,
              ((AccessoryWithStatusTampered) accessory)::unsubscribeStatusTampered));
    }
    if (accessory instanceof AccessoryWithStatusLowBattery) {
      addOptionalCharacteristic(
          new StatusLowBatteryCharacteristic(
              ((AccessoryWithStatusLowBattery) accessory)::getStatusLowBattery,
              ((AccessoryWithStatusLowBattery) accessory)::subscribeStatusLowBattery,
              ((AccessoryWithStatusLowBattery) accessory)::unsubscribeStatusLowBattery));
    }
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
