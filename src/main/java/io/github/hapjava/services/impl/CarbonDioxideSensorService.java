package io.github.hapjava.services.impl;

import io.github.hapjava.accessories.CarbonDioxideSensorAccessory;
import io.github.hapjava.accessories.optionalcharacteristic.AccessoryWithCarbonDioxideLevel;
import io.github.hapjava.accessories.optionalcharacteristic.AccessoryWithName;
import io.github.hapjava.accessories.optionalcharacteristic.AccessoryWithStatusActive;
import io.github.hapjava.accessories.optionalcharacteristic.AccessoryWithStatusFault;
import io.github.hapjava.accessories.optionalcharacteristic.AccessoryWithStatusLowBattery;
import io.github.hapjava.accessories.optionalcharacteristic.AccessoryWithStatusTampered;
import io.github.hapjava.characteristics.impl.battery.StatusLowBatteryCharacteristic;
import io.github.hapjava.characteristics.impl.carbondioxidesensor.CarbonDioxideDetectedCharacteristic;
import io.github.hapjava.characteristics.impl.carbondioxidesensor.CarbonDioxideLevelCharacteristic;
import io.github.hapjava.characteristics.impl.carbondioxidesensor.CarbonDioxidePeakLevelCharacteristic;
import io.github.hapjava.characteristics.impl.common.NameCharacteristic;
import io.github.hapjava.characteristics.impl.common.StatusActiveCharacteristic;
import io.github.hapjava.characteristics.impl.common.StatusFaultCharacteristic;
import io.github.hapjava.characteristics.impl.common.StatusTamperedCharacteristic;

/** This service describes a carbon dioxide sensor. */
public class CarbonDioxideSensorService extends AbstractServiceImpl {

  public CarbonDioxideSensorService(CarbonDioxideDetectedCharacteristic carbonDioxideDetected) {
    super("00000097-0000-1000-8000-0026BB765291");
    addCharacteristic(carbonDioxideDetected);
  }

  public CarbonDioxideSensorService(CarbonDioxideSensorAccessory accessory) {
    this(
        new CarbonDioxideDetectedCharacteristic(
            accessory::getCarbonDioxideDetectedState,
            accessory::subscribeCarbonDioxideDetectedState,
            accessory::unsubscribeCarbonDioxideDetectedState));

    if (accessory instanceof AccessoryWithCarbonDioxideLevel) {
      addOptionalCharacteristic(
          new CarbonDioxideLevelCharacteristic(
              ((AccessoryWithCarbonDioxideLevel) accessory)::getCarbonDioxideLevel,
              ((AccessoryWithCarbonDioxideLevel) accessory)::subscribeCarbonDioxideLevel,
              ((AccessoryWithCarbonDioxideLevel) accessory)::unsubscribeCarbonDioxideLevel));
      addOptionalCharacteristic(
          new CarbonDioxidePeakLevelCharacteristic(
              ((AccessoryWithCarbonDioxideLevel) accessory)::getCarbonDioxidePeakLevel,
              ((AccessoryWithCarbonDioxideLevel) accessory)::subscribeCarbonDioxidePeakLevel,
              ((AccessoryWithCarbonDioxideLevel) accessory)::unsubscribeCarbonDioxidePeakLevel));
    }

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

  public void addOptionalCharacteristic(CarbonDioxideLevelCharacteristic carbonDioxideLevel) {
    addCharacteristic(carbonDioxideLevel);
  }

  public void addOptionalCharacteristic(
      CarbonDioxidePeakLevelCharacteristic carbonDioxidePeakLevel) {
    addCharacteristic(carbonDioxidePeakLevel);
  }
}
