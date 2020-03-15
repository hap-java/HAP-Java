package io.github.hapjava.services.impl;

import io.github.hapjava.accessories.WindowAccessory;
import io.github.hapjava.characteristics.impl.accessoryinformation.NameCharacteristic;
import io.github.hapjava.characteristics.impl.common.ObstructionDetectedCharacteristic;
import io.github.hapjava.characteristics.impl.windowcovering.CurrentPositionCharacteristic;
import io.github.hapjava.characteristics.impl.windowcovering.HoldPositionCharacteristic;
import io.github.hapjava.characteristics.impl.windowcovering.PositionStateCharacteristic;
import io.github.hapjava.characteristics.impl.windowcovering.TargetPositionCharacteristic;

/** This service describes a motorized window. */
public class WindowService extends AbstractServiceImpl {

  public WindowService(
      CurrentPositionCharacteristic currentPositionCharacteristic,
      TargetPositionCharacteristic targetPositionCharacteristic,
      PositionStateCharacteristic positionStateCharacteristic) {
    super("0000008B-0000-1000-8000-0026BB765291");
    addCharacteristic(currentPositionCharacteristic);
    addCharacteristic(targetPositionCharacteristic);
    addCharacteristic(positionStateCharacteristic);
  }

  public WindowService(WindowAccessory window) {
    this(
        new CurrentPositionCharacteristic(
            window::getCurrentPosition,
            window::subscribeCurrentPosition,
            window::unsubscribeCurrentPosition),
        new TargetPositionCharacteristic(
            window::getTargetPosition,
            window::setTargetPosition,
            window::subscribeTargetPosition,
            window::unsubscribeTargetPosition),
        new PositionStateCharacteristic(
            window::getPositionState,
            window::subscribePositionState,
            window::unsubscribePositionState));
  }

  public void addOptionalCharacteristic(NameCharacteristic name) {
    addCharacteristic(name);
  }

  public void addOptionalCharacteristic(HoldPositionCharacteristic holdPositionCharacteristic) {
    addCharacteristic(holdPositionCharacteristic);
  }

  public void addOptionalCharacteristic(
      ObstructionDetectedCharacteristic obstructionDetectedCharacteristic) {
    addCharacteristic(obstructionDetectedCharacteristic);
  }
}
