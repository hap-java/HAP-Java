package io.github.hapjava.services.impl;

import io.github.hapjava.accessories.GarageDoorOpenerAccessory;
import io.github.hapjava.characteristics.impl.common.ObstructionDetectedCharacteristic;
import io.github.hapjava.characteristics.impl.garagedoor.CurrentDoorStateCharacteristic;
import io.github.hapjava.characteristics.impl.garagedoor.TargetDoorStateCharacteristic;
import io.github.hapjava.characteristics.impl.lock.LockCurrentStateCharacteristic;
import io.github.hapjava.characteristics.impl.lock.LockTargetStateCharacteristic;

/**
 * This service describes a garage door opener that controls a single door. If a garage has more
 * than one door, then each door should have its own Garage Door Opener Service.
 */
public class GarageDoorOpenerService extends AbstractServiceImpl {

  public GarageDoorOpenerService(
      CurrentDoorStateCharacteristic currentState,
      TargetDoorStateCharacteristic targetState,
      ObstructionDetectedCharacteristic obstruction) {
    super("00000041-0000-1000-8000-0026BB765291");
    addCharacteristic(currentState);
    addCharacteristic(targetState);
    addCharacteristic(obstruction);
  }

  public GarageDoorOpenerService(GarageDoorOpenerAccessory door) {
    this(
        new CurrentDoorStateCharacteristic(
            door::getCurrentDoorState,
            door::subscribeCurrentDoorState,
            door::unsubscribeCurrentDoorState),
        new TargetDoorStateCharacteristic(
            door::getTargetDoorState,
            door::setTargetDoorState,
            door::subscribeTargetDoorState,
            door::unsubscribeTargetDoorState),
        new ObstructionDetectedCharacteristic(
            door::getObstructionDetected,
            door::subscribeObstructionDetected,
            door::unsubscribeObstructionDetected));
  }

  public void addOptionalCharacteristic(LockCurrentStateCharacteristic lockState) {
    addCharacteristic(lockState);
  }

  public void addOptionalCharacteristic(LockTargetStateCharacteristic lockState) {
    addCharacteristic(lockState);
  }
}
