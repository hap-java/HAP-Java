package io.github.hapjava.services.impl;

import io.github.hapjava.accessories.HumiditySensorAccessory;
import io.github.hapjava.characteristics.impl.battery.StatusLowBatteryCharacteristic;
import io.github.hapjava.characteristics.impl.common.StatusActiveCharacteristic;
import io.github.hapjava.characteristics.impl.common.StatusFaultCharacteristic;
import io.github.hapjava.characteristics.impl.common.StatusTamperedCharacteristic;
import io.github.hapjava.characteristics.impl.humiditysensor.CurrentRelativeHumidityCharacteristic;

/** This service describes a humidity sensor. */
public class HumiditySensorService extends AbstractServiceImpl {

  public HumiditySensorService(
      CurrentRelativeHumidityCharacteristic currentRelativeHumidityCharacteristic) {
    super("00000082-0000-1000-8000-0026BB765291");
    addCharacteristic(currentRelativeHumidityCharacteristic);
  }

  public HumiditySensorService(HumiditySensorAccessory sensor) {
    this(
        new CurrentRelativeHumidityCharacteristic(
            sensor::getCurrentRelativeHumidity,
            sensor::subscribeCurrentRelativeHumidity,
            sensor::unsubscribeCurrentRelativeHumidity));
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
