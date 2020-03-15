package io.github.hapjava.services.impl;

import io.github.hapjava.accessories.LeakSensorAccessory;
import io.github.hapjava.accessories.optionalcharacteristic.AccessoryWithName;
import io.github.hapjava.accessories.optionalcharacteristic.AccessoryWithStatusActive;
import io.github.hapjava.accessories.optionalcharacteristic.AccessoryWithStatusFault;
import io.github.hapjava.characteristics.impl.battery.StatusLowBatteryCharacteristic;
import io.github.hapjava.characteristics.impl.common.NameCharacteristic;
import io.github.hapjava.characteristics.impl.common.StatusActiveCharacteristic;
import io.github.hapjava.characteristics.impl.common.StatusFaultCharacteristic;
import io.github.hapjava.characteristics.impl.common.StatusTamperedCharacteristic;
import io.github.hapjava.characteristics.impl.leaksensor.LeakDetectedStateCharacteristic;

/** This service describes a leak sensor. */
public class LeakSensorService extends AbstractServiceImpl {

  public LeakSensorService(LeakDetectedStateCharacteristic leakDetectedState) {
    super("00000083-0000-1000-8000-0026BB765291");
    addCharacteristic(leakDetectedState);
  }

  public LeakSensorService(LeakSensorAccessory accessory) {
    this(
        new LeakDetectedStateCharacteristic(
            accessory::getLeakDetected,
            accessory::subscribeLeakDetected,
            accessory::unsubscribeLeakDetected));

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
}
