package com.beowulfe.hap.impl.characteristics.windowcovering;

import com.beowulfe.hap.HomekitCharacteristicChangeCallback;
import com.beowulfe.hap.accessories.HorizontalTiltingWindowCovering;
import com.beowulfe.hap.characteristics.EventableCharacteristic;
import com.beowulfe.hap.characteristics.IntegerCharacteristic;
import java.util.concurrent.CompletableFuture;

public class TargetHorizontalTiltAngleCharacteristic extends IntegerCharacteristic
    implements EventableCharacteristic {

  private final HorizontalTiltingWindowCovering windowCovering;

  public TargetHorizontalTiltAngleCharacteristic(HorizontalTiltingWindowCovering windowCovering) {
    super(
        "0000007B-0000-1000-8000-0026BB765291",
        true,
        true,
        "The target horizontal tilt angle",
        -90,
        90,
        "Arc Degree");
    this.windowCovering = windowCovering;
  }

  @Override
  protected void setValue(Integer value) throws Exception {
    windowCovering.setTargetHorizontalTiltAngle(value);
  }

  @Override
  protected CompletableFuture<Integer> getValue() {
    return windowCovering.getTargetHorizontalTiltAngle();
  }

  @Override
  public void subscribe(HomekitCharacteristicChangeCallback callback) {
    windowCovering.subscribeTargetHorizontalTiltAngle(callback);
  }

  @Override
  public void unsubscribe() {
    windowCovering.unsubscribeTargetHorizontalTiltAngle();
  }
}
