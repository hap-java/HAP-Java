package io.github.hapjava.services.impl;

import io.github.hapjava.accessories.CarbonMonoxideSensorAccessory;
import io.github.hapjava.characteristics.impl.battery.StatusLowBatteryCharacteristic;
import io.github.hapjava.characteristics.impl.carbonmonoxidesensor.CarbonMonoxideDetectedCharacteristic;
import io.github.hapjava.characteristics.impl.carbonmonoxidesensor.CarbonMonoxideLevelCharacteristic;
import io.github.hapjava.characteristics.impl.carbonmonoxidesensor.CarbonMonoxidePeakLevelCharacteristic;
import io.github.hapjava.characteristics.impl.common.StatusActiveCharacteristic;
import io.github.hapjava.characteristics.impl.common.StatusFaultCharacteristic;
import io.github.hapjava.characteristics.impl.common.StatusTamperedCharacteristic;

/** This service describes a carbon monoxide sensor. */
public class CarbonMonoxideSensorService extends AbstractServiceImpl {

  public CarbonMonoxideSensorService(CarbonMonoxideDetectedCharacteristic carbonMonoxideDetected) {
    super("0000007F-0000-1000-8000-0026BB765291");
    addCharacteristic(carbonMonoxideDetected);
  }

  public CarbonMonoxideSensorService(CarbonMonoxideSensorAccessory carbonMonoxideSensor) {
    this(
        new CarbonMonoxideDetectedCharacteristic(
            carbonMonoxideSensor::getCarbonMonoxideDetectedState,
            carbonMonoxideSensor::subscribeCarbonMonoxideDetectedState,
            carbonMonoxideSensor::unsubscribeCarbonMonoxideDetectedState));
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
