package io.github.hapjava.impl.characteristics.fan;

import io.github.hapjava.HomekitCharacteristicChangeCallback;
import io.github.hapjava.accessories.Fan;
import io.github.hapjava.characteristics.EventableCharacteristic;
import io.github.hapjava.characteristics.IntegerCharacteristic;
import java.util.concurrent.CompletableFuture;

public class RotationSpeedCharacteristic extends IntegerCharacteristic
    implements EventableCharacteristic {

  private final Fan fan;

  public RotationSpeedCharacteristic(Fan fan) {
    super("00000029-0000-1000-8000-0026BB765291", true, true, "Rotation speed", 0, 100, "%");
    this.fan = fan;
  }

  @Override
  public void subscribe(HomekitCharacteristicChangeCallback callback) {
    fan.subscribeRotationSpeed(callback);
  }

  @Override
  public void unsubscribe() {
    fan.unsubscribeRotationSpeed();
  }

  @Override
  protected void setValue(Integer value) throws Exception {
    fan.setRotationSpeed(value);
  }

  @Override
  protected CompletableFuture<Integer> getValue() {
    return fan.getRotationSpeed();
  }
}
