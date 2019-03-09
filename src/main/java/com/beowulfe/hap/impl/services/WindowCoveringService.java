package com.beowulfe.hap.impl.services;

import com.beowulfe.hap.accessories.BasicWindowCovering;
import com.beowulfe.hap.accessories.HoldPositionWindowCovering;
import com.beowulfe.hap.accessories.HorizontalTiltingWindowCovering;
import com.beowulfe.hap.accessories.ObstructionDetectedWindowCovering;
import com.beowulfe.hap.accessories.VerticalTiltingWindowCovering;
import com.beowulfe.hap.impl.characteristics.common.ObstructionDetectedCharacteristic;
import com.beowulfe.hap.impl.characteristics.windowcovering.CurrentHorizontalTiltAngleCharacteristic;
import com.beowulfe.hap.impl.characteristics.windowcovering.CurrentPositionCharacteristic;
import com.beowulfe.hap.impl.characteristics.windowcovering.CurrentVerticalTiltAngleCharacteristic;
import com.beowulfe.hap.impl.characteristics.windowcovering.HoldPositionCharacteristic;
import com.beowulfe.hap.impl.characteristics.windowcovering.PositionStateCharacteristic;
import com.beowulfe.hap.impl.characteristics.windowcovering.TargetHorizontalTiltAngleCharacteristic;
import com.beowulfe.hap.impl.characteristics.windowcovering.TargetPositionCharacteristic;
import com.beowulfe.hap.impl.characteristics.windowcovering.TargetVerticalTiltAngleCharacteristic;

public class WindowCoveringService extends AbstractServiceImpl {

  public WindowCoveringService(BasicWindowCovering windowCovering) {
    this(windowCovering, windowCovering.getLabel());
  }

  public WindowCoveringService(BasicWindowCovering windowCovering, String serviceName) {
    super("0000008C-0000-1000-8000-0026BB765291", windowCovering, serviceName);
    addCharacteristic(new CurrentPositionCharacteristic(windowCovering));
    addCharacteristic(new PositionStateCharacteristic(windowCovering));
    addCharacteristic(new TargetPositionCharacteristic(windowCovering));

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
    if (windowCovering instanceof HoldPositionWindowCovering) {
      HoldPositionWindowCovering hpwc = (HoldPositionWindowCovering) windowCovering;
      addCharacteristic(new HoldPositionCharacteristic(hpwc));
    }
    if (windowCovering instanceof ObstructionDetectedWindowCovering) {
      ObstructionDetectedWindowCovering wc = (ObstructionDetectedWindowCovering) windowCovering;
      addCharacteristic(
          new ObstructionDetectedCharacteristic(
              () -> wc.getObstructionDetected(),
              c -> wc.subscribeObstructionDetected(c),
              () -> wc.unsubscribeObstructionDetected()));
    }
  }
}
