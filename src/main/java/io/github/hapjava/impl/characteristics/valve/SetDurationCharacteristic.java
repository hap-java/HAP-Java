package io.github.hapjava.impl.characteristics.valve;

import io.github.hapjava.HomekitCharacteristicChangeCallback;
import io.github.hapjava.accessories.ValveWithTimer;
import io.github.hapjava.characteristics.EventableCharacteristic;
import io.github.hapjava.characteristics.IntegerCharacteristic;
import java.util.concurrent.CompletableFuture;

public class SetDurationCharacteristic extends IntegerCharacteristic
    implements EventableCharacteristic {
  private final ValveWithTimer valve;

  public SetDurationCharacteristic(ValveWithTimer valve) {
    super("000000D3-0000-1000-8000-0026BB765291", true, true, "Set Duration", 0, 3600, "seconds");
    this.valve = valve;
  }

  @Override
  public void subscribe(HomekitCharacteristicChangeCallback callback) {
    valve.subscribeSetDuration(callback);
  }

  @Override
  public void unsubscribe() {
    valve.unsubscribeSetDuration();
  }

  @Override
  protected void setValue(Integer value) throws Exception {
    valve.setSetDuration(value);
  }

  @Override
  protected CompletableFuture<Integer> getValue() {
    return valve.getSetDuration();
  }
}
