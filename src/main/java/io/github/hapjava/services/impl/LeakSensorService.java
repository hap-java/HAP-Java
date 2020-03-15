package io.github.hapjava.services.impl;

import io.github.hapjava.accessories.LeakSensorAccessory;
import io.github.hapjava.characteristics.impl.battery.StatusLowBatteryCharacteristic;
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

  public LeakSensorService(LeakSensorAccessory leakSensor) {
    this(
        new LeakDetectedStateCharacteristic(
            leakSensor::getLeakDetected,
            leakSensor::subscribeLeakDetected,
            leakSensor::unsubscribeLeakDetected));
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
