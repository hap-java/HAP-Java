package io.github.hapjava.services.impl;

import io.github.hapjava.accessories.CarbonMonoxideSensorAccessory;
import io.github.hapjava.accessories.optionalcharacteristic.AccessoryWithCarbonMonoxideLevel;
import io.github.hapjava.accessories.optionalcharacteristic.AccessoryWithName;
import io.github.hapjava.accessories.optionalcharacteristic.AccessoryWithStatusActive;
import io.github.hapjava.accessories.optionalcharacteristic.AccessoryWithStatusFault;
import io.github.hapjava.characteristics.impl.battery.StatusLowBatteryCharacteristic;
import io.github.hapjava.characteristics.impl.carbonmonoxidesensor.CarbonMonoxideDetectedCharacteristic;
import io.github.hapjava.characteristics.impl.carbonmonoxidesensor.CarbonMonoxideLevelCharacteristic;
import io.github.hapjava.characteristics.impl.carbonmonoxidesensor.CarbonMonoxidePeakLevelCharacteristic;
import io.github.hapjava.characteristics.impl.common.NameCharacteristic;
import io.github.hapjava.characteristics.impl.common.StatusActiveCharacteristic;
import io.github.hapjava.characteristics.impl.common.StatusFaultCharacteristic;
import io.github.hapjava.characteristics.impl.common.StatusTamperedCharacteristic;

/** This service describes a carbon monoxide sensor. */
public class CarbonMonoxideSensorService extends AbstractServiceImpl {

  public CarbonMonoxideSensorService(CarbonMonoxideDetectedCharacteristic carbonMonoxideDetected) {
    super("0000007F-0000-1000-8000-0026BB765291");
    addCharacteristic(carbonMonoxideDetected);
  }

  public CarbonMonoxideSensorService(CarbonMonoxideSensorAccessory accessory) {
    this(
        new CarbonMonoxideDetectedCharacteristic(
            accessory::getCarbonMonoxideDetectedState,
            accessory::subscribeCarbonMonoxideDetectedState,
            accessory::unsubscribeCarbonMonoxideDetectedState));

    if (accessory instanceof AccessoryWithCarbonMonoxideLevel) {
      addOptionalCharacteristic(
          new CarbonMonoxideLevelCharacteristic(
              ((AccessoryWithCarbonMonoxideLevel) accessory)::getCarbonMonoxideLevel,
              ((AccessoryWithCarbonMonoxideLevel) accessory)::subscribeCarbonMonoxideLevel,
              ((AccessoryWithCarbonMonoxideLevel) accessory)::unsubscribeCarbonMonoxideLevel));
      addOptionalCharacteristic(
          new CarbonMonoxidePeakLevelCharacteristic(
              ((AccessoryWithCarbonMonoxideLevel) accessory)::getCarbonMonoxidePeakLevel,
              ((AccessoryWithCarbonMonoxideLevel) accessory)::subscribeCarbonMonoxidePeakLevel,
              ((AccessoryWithCarbonMonoxideLevel) accessory)::unsubscribeCarbonMonoxidePeakLevel));
    }

    if (accessory instanceof AccessoryWithStatusActive) {
      addOptionalCharacteristic(
          new StatusActiveCharacteristic(
              ((AccessoryWithStatusActive) accessory)::getStatusActive,
              ((AccessoryWithStatusActive) accessory)::subscribeStatusActive,
              ((AccessoryWithStatusActive) accessory)::unsubscribeStatusActive));
    }
    if (accessory instanceof AccessoryWithName) {
      addOptionalCharacteristic(new NameCharacteristic(((AccessoryWithName) accessory)::getName));
    }
    if (accessory instanceof AccessoryWithStatusFault) {
      addOptionalCharacteristic(
          new StatusFaultCharacteristic(
              ((AccessoryWithStatusFault) accessory)::getStatusFault,
              ((AccessoryWithStatusFault) accessory)::subscribeStatusFault,
              ((AccessoryWithStatusFault) accessory)::unsubscribeStatusFault));
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

  public void addOptionalCharacteristic(CarbonMonoxideLevelCharacteristic carbonMonoxideLevel) {
    addCharacteristic(carbonMonoxideLevel);
  }

  public void addOptionalCharacteristic(
      CarbonMonoxidePeakLevelCharacteristic carbonMonoxidePeakLevel) {
    addCharacteristic(carbonMonoxidePeakLevel);
  }
}
