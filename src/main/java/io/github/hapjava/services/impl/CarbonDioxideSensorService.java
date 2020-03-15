package io.github.hapjava.services.impl;

import io.github.hapjava.accessories.CarbonDioxideSensorAccessory;
import io.github.hapjava.accessories.optionalcharacteristic.AccessoryWithCarbonDioxideLevel;
import io.github.hapjava.characteristics.impl.battery.StatusLowBatteryCharacteristic;
import io.github.hapjava.characteristics.impl.carbondioxidesensor.CarbonDioxideDetectedCharacteristic;
import io.github.hapjava.characteristics.impl.carbondioxidesensor.CarbonDioxideLevelCharacteristic;
import io.github.hapjava.characteristics.impl.carbondioxidesensor.CarbonDioxidePeakLevelCharacteristic;
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

  public void addOptionalCharacteristic(CarbonDioxideLevelCharacteristic carbonMonoxideLevel) {
    addCharacteristic(carbonMonoxideLevel);
  }

  public void addOptionalCharacteristic(
      CarbonDioxidePeakLevelCharacteristic carbonDioxidePeakLevel) {
    addCharacteristic(carbonDioxidePeakLevel);
  }
}
