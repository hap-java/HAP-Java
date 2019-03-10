package io.github.hapjava.impl.services;

import io.github.hapjava.accessories.BasicWindowCovering;
import io.github.hapjava.accessories.HoldPositionWindowCovering;
import io.github.hapjava.accessories.HorizontalTiltingWindowCovering;
import io.github.hapjava.accessories.ObstructionDetectedWindowCovering;
import io.github.hapjava.accessories.VerticalTiltingWindowCovering;
import io.github.hapjava.impl.characteristics.common.ObstructionDetectedCharacteristic;
import io.github.hapjava.impl.characteristics.windowcovering.CurrentHorizontalTiltAngleCharacteristic;
import io.github.hapjava.impl.characteristics.windowcovering.CurrentPositionCharacteristic;
import io.github.hapjava.impl.characteristics.windowcovering.CurrentVerticalTiltAngleCharacteristic;
import io.github.hapjava.impl.characteristics.windowcovering.HoldPositionCharacteristic;
import io.github.hapjava.impl.characteristics.windowcovering.PositionStateCharacteristic;
import io.github.hapjava.impl.characteristics.windowcovering.TargetHorizontalTiltAngleCharacteristic;
import io.github.hapjava.impl.characteristics.windowcovering.TargetPositionCharacteristic;
import io.github.hapjava.impl.characteristics.windowcovering.TargetVerticalTiltAngleCharacteristic;

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
