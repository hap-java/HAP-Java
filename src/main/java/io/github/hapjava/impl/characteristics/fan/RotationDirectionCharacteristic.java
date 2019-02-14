package io.github.hapjava.impl.characteristics.fan;

import io.github.hapjava.HomekitCharacteristicChangeCallback;
import io.github.hapjava.accessories.characteristics.RotationDirection;
import io.github.hapjava.characteristics.EnumCharacteristic;
import io.github.hapjava.characteristics.EventableCharacteristic;
import java.util.concurrent.CompletableFuture;

public class RotationDirectionCharacteristic extends EnumCharacteristic
    implements EventableCharacteristic {

  private final RotationDirection fan;

  public RotationDirectionCharacteristic(RotationDirection fan) {
    super("00000028-0000-1000-8000-0026BB765291", true, true, "Rotation Direction", 1);
    this.fan = fan;
  }

  @Override
  protected void setValue(Integer value) throws Exception {
    fan.setRotationDirection(
        io.github.hapjava.accessories.properties.RotationDirection.fromCode(value));
  }

  @Override
  protected CompletableFuture<Integer> getValue() {
    return fan.getRotationDirection().thenApply(s -> s.getCode());
  }

  @Override
  public void subscribe(HomekitCharacteristicChangeCallback callback) {
    fan.subscribeRotationDirection(callback);
  }

  @Override
  public void unsubscribe() {
    fan.unsubscribeRotationDirection();
  }
}
