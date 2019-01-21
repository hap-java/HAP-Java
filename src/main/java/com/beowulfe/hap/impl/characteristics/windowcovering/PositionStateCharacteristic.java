package com.beowulfe.hap.impl.characteristics.windowcovering;

import com.beowulfe.hap.HomekitCharacteristicChangeCallback;
import com.beowulfe.hap.accessories.WindowCovering;
import com.beowulfe.hap.characteristics.EnumCharacteristic;
import com.beowulfe.hap.characteristics.EventableCharacteristic;
import java.util.concurrent.CompletableFuture;

public class PositionStateCharacteristic extends EnumCharacteristic
    implements EventableCharacteristic {

  private final WindowCovering windowCovering;

  public PositionStateCharacteristic(WindowCovering windowCovering) {
    super("00000072-0000-1000-8000-0026BB765291", false, true, "The position state", 2);
    this.windowCovering = windowCovering;
  }

  @Override
  protected void setValue(Integer value) throws Exception {
    // Read only
  }

  @Override
  protected CompletableFuture<Integer> getValue() {
    return windowCovering.getPositionState().thenApply(v -> v.getCode());
  }

  @Override
  public void subscribe(HomekitCharacteristicChangeCallback callback) {
    windowCovering.subscribePositionState(callback);
  }

  @Override
  public void unsubscribe() {
    windowCovering.unsubscribePositionState();
  }
}
