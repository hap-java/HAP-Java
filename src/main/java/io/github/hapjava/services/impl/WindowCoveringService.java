package io.github.hapjava.services.impl;

import io.github.hapjava.accessories.WindowCoveringAccessory;
import io.github.hapjava.accessories.optionalcharacteristic.AccessoryWithName;
import io.github.hapjava.accessories.optionalcharacteristic.AccessoryWithObstructionDetection;
import io.github.hapjava.characteristics.impl.common.NameCharacteristic;
import io.github.hapjava.characteristics.impl.common.ObstructionDetectedCharacteristic;
import io.github.hapjava.characteristics.impl.windowcovering.CurrentHorizontalTiltAngleCharacteristic;
import io.github.hapjava.characteristics.impl.windowcovering.CurrentPositionCharacteristic;
import io.github.hapjava.characteristics.impl.windowcovering.CurrentVerticalTiltAngleCharacteristic;
import io.github.hapjava.characteristics.impl.windowcovering.HoldPositionCharacteristic;
import io.github.hapjava.characteristics.impl.windowcovering.PositionStateCharacteristic;
import io.github.hapjava.characteristics.impl.windowcovering.TargetHorizontalTiltAngleCharacteristic;
import io.github.hapjava.characteristics.impl.windowcovering.TargetPositionCharacteristic;
import io.github.hapjava.characteristics.impl.windowcovering.TargetVerticalTiltAngleCharacteristic;

/**
 * This service describes motorized window coverings or shades - examples include shutters, blinds,
 * awnings etc.
 */
public class WindowCoveringService extends AbstractServiceImpl {

  public WindowCoveringService(
      TargetPositionCharacteristic targetPositionCharacteristic,
      CurrentPositionCharacteristic currentPositionCharacteristic,
      PositionStateCharacteristic positionStateCharacteristic) {
    super("0000008C-0000-1000-8000-0026BB765291");
    addCharacteristic(targetPositionCharacteristic);
    addCharacteristic(currentPositionCharacteristic);
    addCharacteristic(positionStateCharacteristic);
  }

  public WindowCoveringService(WindowCoveringAccessory accessory) {
    this(
        new TargetPositionCharacteristic(
            accessory::getTargetPosition,
            accessory::setTargetPosition,
            accessory::subscribeTargetPosition,
            accessory::unsubscribeTargetPosition),
        new CurrentPositionCharacteristic(
            accessory::getCurrentPosition,
            accessory::subscribeCurrentPosition,
            accessory::unsubscribeCurrentPosition),
        new PositionStateCharacteristic(
            accessory::getPositionState,
            accessory::subscribePositionState,
            accessory::unsubscribePositionState));
    if (accessory instanceof AccessoryWithName) {
      addOptionalCharacteristic(new NameCharacteristic(((AccessoryWithName) accessory)::getName));
    }
    if (accessory instanceof AccessoryWithObstructionDetection) {
      addOptionalCharacteristic(
          new ObstructionDetectedCharacteristic(
              ((AccessoryWithObstructionDetection) accessory)::getObstructionDetected,
              ((AccessoryWithObstructionDetection) accessory)::subscribeObstructionDetected,
              ((AccessoryWithObstructionDetection) accessory)::unsubscribeObstructionDetected));
    }
  }

  public void addOptionalCharacteristic(NameCharacteristic name) {
    addCharacteristic(name);
  }

  public void addOptionalCharacteristic(HoldPositionCharacteristic holdPositionCharacteristic) {
    addCharacteristic(holdPositionCharacteristic);
  }

  public void addOptionalCharacteristic(
      CurrentHorizontalTiltAngleCharacteristic currentHorizontalTiltAngleCharacteristic) {
    addCharacteristic(currentHorizontalTiltAngleCharacteristic);
  }

  public void addOptionalCharacteristic(
      TargetHorizontalTiltAngleCharacteristic targetHorizontalTiltAngleCharacteristic) {
    addCharacteristic(targetHorizontalTiltAngleCharacteristic);
  }

  public void addOptionalCharacteristic(
      CurrentVerticalTiltAngleCharacteristic currentVerticalTiltAngleCharacteristic) {
    addCharacteristic(currentVerticalTiltAngleCharacteristic);
  }

  public void addOptionalCharacteristic(
      TargetVerticalTiltAngleCharacteristic targetVerticalTiltAngleCharacteristic) {
    addCharacteristic(targetVerticalTiltAngleCharacteristic);
  }

  public void addOptionalCharacteristic(
      ObstructionDetectedCharacteristic obstructionDetectedCharacteristic) {
    addCharacteristic(obstructionDetectedCharacteristic);
  }
}
