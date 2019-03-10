package io.github.hapjava.impl.characteristics.leaksensor;

import io.github.hapjava.HomekitCharacteristicChangeCallback;
import io.github.hapjava.accessories.LeakSensor;
import io.github.hapjava.characteristics.BooleanCharacteristic;
import io.github.hapjava.characteristics.EventableCharacteristic;
import java.util.concurrent.CompletableFuture;

public class LeakDetectedStateCharacteristic extends BooleanCharacteristic
    implements EventableCharacteristic {

  private final LeakSensor leakSensor;

  public LeakDetectedStateCharacteristic(LeakSensor leakSensor) {
    super("00000070-0000-1000-8000-0026BB765291", false, true, "Leak Detected");
    this.leakSensor = leakSensor;
  }

  @Override
  protected CompletableFuture<Boolean> getValue() {
    return leakSensor.getLeakDetected();
  }

  @Override
  protected void setValue(Boolean value) throws Exception {
    // Read Only
  }

  @Override
  public void subscribe(HomekitCharacteristicChangeCallback callback) {
    leakSensor.subscribeLeakDetected(callback);
  }

  @Override
  public void unsubscribe() {
    leakSensor.unsubscribeLeakDetected();
  }
}
