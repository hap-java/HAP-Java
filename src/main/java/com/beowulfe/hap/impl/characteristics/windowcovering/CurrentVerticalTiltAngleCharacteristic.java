package com.beowulfe.hap.impl.characteristics.windowcovering;

import com.beowulfe.hap.HomekitCharacteristicChangeCallback;
import com.beowulfe.hap.accessories.VerticalTiltingWindowCovering;
import com.beowulfe.hap.characteristics.EventableCharacteristic;
import com.beowulfe.hap.characteristics.IntegerCharacteristic;
import java.util.concurrent.CompletableFuture;

public class CurrentVerticalTiltAngleCharacteristic extends IntegerCharacteristic
    implements EventableCharacteristic {

  private final VerticalTiltingWindowCovering windowCovering;

  public CurrentVerticalTiltAngleCharacteristic(VerticalTiltingWindowCovering windowCovering) {
    super(
        "0000006E-0000-1000-8000-0026BB765291",
        false,
        true,
        "The current vertical tilt angle",
        -90,
        90,
        "Arc Degree");
    this.windowCovering = windowCovering;
  }

  @Override
  protected void setValue(Integer value) throws Exception {
    // Read Only
  }

  @Override
  protected CompletableFuture<Integer> getValue() {
    return windowCovering.getCurrentVerticalTiltAngle();
  }

  @Override
  public void subscribe(HomekitCharacteristicChangeCallback callback) {
    windowCovering.subscribeCurrentVerticalTiltAngle(callback);
  }

  @Override
  public void unsubscribe() {
    windowCovering.unsubscribeCurrentVerticalTiltAngle();
  }
}
