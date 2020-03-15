package io.github.hapjava.services.impl;

import io.github.hapjava.accessories.BatteryAccessory;
import io.github.hapjava.characteristics.impl.battery.BatteryLevelCharacteristic;
import io.github.hapjava.characteristics.impl.battery.ChargingStateCharacteristic;
import io.github.hapjava.characteristics.impl.battery.StatusLowBatteryCharacteristic;

/** This service describes a battery service. */
public class BatteryService extends AbstractServiceImpl {

  public BatteryService(
      BatteryLevelCharacteristic batteryLevelCharacteristic,
      ChargingStateCharacteristic chargingStateCharacteristic,
      StatusLowBatteryCharacteristic statusLowBatteryCharacteristic) {
    super("00000096-0000-1000-8000-0026BB765291");
    addCharacteristic(batteryLevelCharacteristic);
    addCharacteristic(chargingStateCharacteristic);
    addCharacteristic(statusLowBatteryCharacteristic);
  }

  public BatteryService(BatteryAccessory batteryAccessory) {
    this(
        new BatteryLevelCharacteristic(
            batteryAccessory::getBatteryLevel,
            batteryAccessory::subscribeBatteryLevel,
            batteryAccessory::unsubscribeBatteryLevel),
        new ChargingStateCharacteristic(
            batteryAccessory::getChargingState,
            batteryAccessory::subscribeBatteryChargingState,
            batteryAccessory::unsubscribeBatteryChargingState),
        new StatusLowBatteryCharacteristic(
            batteryAccessory::getLowBatteryState,
            batteryAccessory::subscribeLowBatteryState,
            batteryAccessory::unsubscribeLowBatteryState));
  }
}
