package io.github.hapjava.impl.characteristics.windowcovering;

import io.github.hapjava.HomekitCharacteristicChangeCallback;
import io.github.hapjava.accessories.VerticalTiltingWindowCovering;
import io.github.hapjava.characteristics.EventableCharacteristic;
import io.github.hapjava.characteristics.IntegerCharacteristic;
import java.util.concurrent.CompletableFuture;

public class TargetVerticalTiltAngleCharacteristic extends IntegerCharacteristic
    implements EventableCharacteristic {

  private final VerticalTiltingWindowCovering windowCovering;

  public TargetVerticalTiltAngleCharacteristic(VerticalTiltingWindowCovering windowCovering) {
    super(
        "0000007D-0000-1000-8000-0026BB765291",
        true,
        true,
        "The target vertical tilt angle",
        -90,
        90,
        "Arc Degree");
    this.windowCovering = windowCovering;
  }

  @Override
  protected void setValue(Integer value) throws Exception {
    windowCovering.setTargetVerticalTiltAngle(value);
  }

  @Override
  protected CompletableFuture<Integer> getValue() {
    return windowCovering.getTargetVerticalTiltAngle();
  }

  @Override
  public void subscribe(HomekitCharacteristicChangeCallback callback) {
    windowCovering.subscribeTargetVerticalTiltAngle(callback);
  }

  @Override
  public void unsubscribe() {
    windowCovering.unsubscribeTargetVerticalTiltAngle();
  }
}
