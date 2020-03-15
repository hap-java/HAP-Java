package io.github.hapjava.services.impl;

import io.github.hapjava.accessories.DoorAccessory;
import io.github.hapjava.characteristics.impl.common.ObstructionDetectedCharacteristic;
import io.github.hapjava.characteristics.impl.windowcovering.CurrentPositionCharacteristic;
import io.github.hapjava.characteristics.impl.windowcovering.HoldPositionCharacteristic;
import io.github.hapjava.characteristics.impl.windowcovering.PositionStateCharacteristic;
import io.github.hapjava.characteristics.impl.windowcovering.TargetPositionCharacteristic;

/** This service describes a motorized door. */
public class DoorService extends AbstractServiceImpl {

  public DoorService(
      CurrentPositionCharacteristic currentPositionCharacteristic,
      TargetPositionCharacteristic targetPositionCharacteristic,
      PositionStateCharacteristic positionStateCharacteristic) {
    super("00000081-0000-1000-8000-0026BB765291");
    addCharacteristic(currentPositionCharacteristic);
    addCharacteristic(targetPositionCharacteristic);
    addCharacteristic(positionStateCharacteristic);
  }

  public DoorService(DoorAccessory door) {
    this(
        new CurrentPositionCharacteristic(
            door::getCurrentPosition,
            door::subscribeCurrentPosition,
            door::unsubscribeCurrentPosition),
        new TargetPositionCharacteristic(
            door::getTargetPosition,
            door::setTargetPosition,
            door::subscribeTargetPosition,
            door::unsubscribeTargetPosition),
        new PositionStateCharacteristic(
            door::getPositionState, door::subscribePositionState, door::unsubscribePositionState));
  }

  public void addOptionalCharacteristic(HoldPositionCharacteristic holdPositionCharacteristic) {
    addCharacteristic(holdPositionCharacteristic);
  }

  public void addOptionalCharacteristic(
      ObstructionDetectedCharacteristic obstructionDetectedCharacteristic) {
    addCharacteristic(obstructionDetectedCharacteristic);
  }
}
