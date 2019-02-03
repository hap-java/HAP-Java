package com.beowulfe.hap.impl.characteristics.windowcovering;

import com.beowulfe.hap.accessories.WindowCovering;
import com.beowulfe.hap.characteristics.BooleanCharacteristic;
import java.util.concurrent.CompletableFuture;

public class HoldPositionCharacteristic extends BooleanCharacteristic {

  private final WindowCovering windowCovering;

  public HoldPositionCharacteristic(WindowCovering windowCovering) {
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
