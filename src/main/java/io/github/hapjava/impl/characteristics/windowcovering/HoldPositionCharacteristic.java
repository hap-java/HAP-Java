package io.github.hapjava.impl.characteristics.windowcovering;

import io.github.hapjava.accessories.HoldPositionWindowCovering;
import io.github.hapjava.characteristics.BooleanCharacteristic;
import java.util.concurrent.CompletableFuture;

public class HoldPositionCharacteristic extends BooleanCharacteristic {

  private final HoldPositionWindowCovering windowCovering;

  public HoldPositionCharacteristic(HoldPositionWindowCovering windowCovering) {
    super("0000006F-0000-1000-8000-0026BB765291", true, false, "Whether or not to hold position");
    this.windowCovering = windowCovering;
  }

  @Override
  protected void setValue(Boolean value) throws Exception {
    this.windowCovering.setHoldPosition(value);
  }

  @Override
  protected CompletableFuture<Boolean> getValue() {
    // Write only
    return CompletableFuture.completedFuture(null);
  }
}
