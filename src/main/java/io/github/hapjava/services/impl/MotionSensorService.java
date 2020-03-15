package io.github.hapjava.services.impl;

import io.github.hapjava.accessories.MotionSensorAccessory;
import io.github.hapjava.characteristics.impl.accessoryinformation.NameCharacteristic;
import io.github.hapjava.characteristics.impl.battery.StatusLowBatteryCharacteristic;
import io.github.hapjava.characteristics.impl.common.StatusActiveCharacteristic;
import io.github.hapjava.characteristics.impl.common.StatusFaultCharacteristic;
import io.github.hapjava.characteristics.impl.common.StatusTamperedCharacteristic;
import io.github.hapjava.characteristics.impl.motionsensor.MotionDetectedCharacteristic;

/** This service describes a motion sensor. */
public class MotionSensorService extends AbstractServiceImpl {

  public MotionSensorService(MotionDetectedCharacteristic motionDetectedCharacteristic) {
    super("00000085-0000-1000-8000-0026BB765291");
    addCharacteristic(motionDetectedCharacteristic);
  }

  public MotionSensorService(MotionSensorAccessory motionSensor) {
    this(
        new MotionDetectedCharacteristic(
            motionSensor::getMotionDetected,
            motionSensor::subscribeMotionDetected,
            motionSensor::unsubscribeMotionDetected));
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
