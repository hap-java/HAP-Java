package com.beowulfe.hap.impl.characteristics.windowcovering;

import com.beowulfe.hap.HomekitCharacteristicChangeCallback;
import com.beowulfe.hap.accessories.WindowCovering;
import com.beowulfe.hap.characteristics.EventableCharacteristic;
import com.beowulfe.hap.characteristics.IntegerCharacteristic;
import java.util.concurrent.CompletableFuture;

public class TargetPositionCharacteristic extends IntegerCharacteristic
    implements EventableCharacteristic {

  private final WindowCovering windowCovering;

  public TargetPositionCharacteristic(WindowCovering windowCovering) {
    super("0000007C-0000-1000-8000-0026BB765291", true, true, "The target position", 0, 100, "%");
    this.windowCovering = windowCovering;
  }

  @Override
  protected void setValue(Integer value) throws Exception {
    windowCovering.setTargetPosition(value);
  }

  @Override
  protected CompletableFuture<Integer> getValue() {
    return windowCovering.getTargetPosition();
  }

  @Override
  public void subscribe(HomekitCharacteristicChangeCallback callback) {
    windowCovering.subscribeTargetPosition(callback);
  }

  @Override
  public void unsubscribe() {
    windowCovering.unsubscribeTargetPosition();
  }
}
