package io.github.hapjava.characteristics.impl.fan;

import io.github.hapjava.characteristics.impl.base.EnumCharacteristic;
import io.github.hapjava.characteristics.EventableCharacteristic;
import io.github.hapjava.characteristics.ExceptionalConsumer;
import io.github.hapjava.characteristics.HomekitCharacteristicChangeCallback;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import java.util.function.Supplier;

/** This characteristic describes the direction of rotation of a fan. */
public class RotationDirectionCharacteristic extends EnumCharacteristic<RotationDirectionEnum>
    implements EventableCharacteristic {

  public RotationDirectionCharacteristic(
      Supplier<CompletableFuture<RotationDirectionEnum>> getter,
      ExceptionalConsumer<RotationDirectionEnum> setter,
      Consumer<HomekitCharacteristicChangeCallback> subscriber,
      Runnable unsubscriber) {
    super(
        "00000028-0000-1000-8000-0026BB765291",
        "Rotation direction",
        1,
        Optional.of(getter),
        Optional.of(setter),
        Optional.of(subscriber),
        Optional.of(unsubscriber));
  }

  @Override
  protected void setValue(Integer value) throws Exception {
    if (!setter.isPresent()) {
      return;
    }
    setter.get().accept(RotationDirectionEnum.fromCode(value));
  }
}
