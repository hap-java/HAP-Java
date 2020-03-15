package io.github.hapjava.services.impl;

import io.github.hapjava.accessories.OccupancySensorAccessory;
import io.github.hapjava.characteristics.impl.accessoryinformation.NameCharacteristic;
import io.github.hapjava.characteristics.impl.battery.StatusLowBatteryCharacteristic;
import io.github.hapjava.characteristics.impl.common.StatusActiveCharacteristic;
import io.github.hapjava.characteristics.impl.common.StatusFaultCharacteristic;
import io.github.hapjava.characteristics.impl.common.StatusTamperedCharacteristic;
import io.github.hapjava.characteristics.impl.occupancysensor.OccupancyDetectedCharacteristic;

/** This service describes an occupancy sensor. */
public class OccupancySensorService extends AbstractServiceImpl {

  public OccupancySensorService(OccupancyDetectedCharacteristic occupancyDetectedCharacteristic) {
    super("00000086-0000-1000-8000-0026BB765291");
    addCharacteristic(occupancyDetectedCharacteristic);
  }

  public OccupancySensorService(OccupancySensorAccessory occupancySensor) {
    this(
        new OccupancyDetectedCharacteristic(
            occupancySensor::getOccupancyDetected,
            occupancySensor::subscribeOccupancyDetected,
            occupancySensor::unsubscribeOccupancyDetected));
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
