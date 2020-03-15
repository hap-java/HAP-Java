package io.github.hapjava.services.impl;

import io.github.hapjava.accessories.BatteryAccessory;
import io.github.hapjava.accessories.optionalcharacteristic.AccessoryWithName;
import io.github.hapjava.characteristics.impl.battery.BatteryLevelCharacteristic;
import io.github.hapjava.characteristics.impl.battery.ChargingStateCharacteristic;
import io.github.hapjava.characteristics.impl.battery.StatusLowBatteryCharacteristic;
import io.github.hapjava.characteristics.impl.common.NameCharacteristic;

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

  public BatteryService(BatteryAccessory accessory) {
    this(
        new BatteryLevelCharacteristic(
            accessory::getBatteryLevel,
            accessory::subscribeBatteryLevel,
            accessory::unsubscribeBatteryLevel),
        new ChargingStateCharacteristic(
            accessory::getChargingState,
            accessory::subscribeBatteryChargingState,
            accessory::unsubscribeBatteryChargingState),
        new StatusLowBatteryCharacteristic(
            accessory::getLowBatteryState,
            accessory::subscribeLowBatteryState,
            accessory::unsubscribeLowBatteryState));
    if (accessory instanceof AccessoryWithName) {
      addOptionalCharacteristic(new NameCharacteristic(((AccessoryWithName) accessory)::getName));
    }
  }

  public void addOptionalCharacteristic(NameCharacteristic name) {
    addCharacteristic(name);
  }
}
