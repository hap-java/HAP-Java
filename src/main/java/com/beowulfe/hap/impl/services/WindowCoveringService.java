package com.beowulfe.hap.impl.services;

import com.beowulfe.hap.accessories.HorizontalTiltingWindowCovering;
import com.beowulfe.hap.accessories.VerticalTiltingWindowCovering;
import com.beowulfe.hap.accessories.WindowCovering;
import com.beowulfe.hap.impl.characteristics.common.ObstructionDetectedCharacteristic;
import com.beowulfe.hap.impl.characteristics.windowcovering.*;

public class WindowCoveringService extends AbstractServiceImpl {

  public WindowCoveringService(WindowCovering windowCovering) {
    this(windowCovering, windowCovering.getLabel());
  }

  public WindowCoveringService(WindowCovering windowCovering, String serviceName) {
    super("0000008C-0000-1000-8000-0026BB765291", windowCovering, serviceName);
    addCharacteristic(new CurrentPositionCharacteristic(windowCovering));
    addCharacteristic(new HoldPositionCharacteristic(windowCovering));
    addCharacteristic(new PositionStateCharacteristic(windowCovering));
    addCharacteristic(new TargetPositionCharacteristic(windowCovering));
    addCharacteristic(
        new ObstructionDetectedCharacteristic(
            () -> windowCovering.getObstructionDetected(),
            c -> windowCovering.subscribeObstructionDetected(c),
            () -> windowCovering.unsubscribeObstructionDetected()));

    if (windowCovering instanceof HorizontalTiltingWindowCovering) {
      addCharacteristic(
          new CurrentHorizontalTiltAngleCharacteristic(
              (HorizontalTiltingWindowCovering) windowCovering));
      addCharacteristic(
          new TargetHorizontalTiltAngleCharacteristic(
              (HorizontalTiltingWindowCovering) windowCovering));
    }
    if (windowCovering instanceof VerticalTiltingWindowCovering) {
      addCharacteristic(
          new CurrentVerticalTiltAngleCharacteristic(
              (VerticalTiltingWindowCovering) windowCovering));
      addCharacteristic(
          new TargetVerticalTiltAngleCharacteristic(
              (VerticalTiltingWindowCovering) windowCovering));
    }
  }
}
