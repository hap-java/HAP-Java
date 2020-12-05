package io.github.hapjava.characteristics.impl.fan;

import io.github.hapjava.characteristics.EventableCharacteristic;
import io.github.hapjava.characteristics.ExceptionalConsumer;
import io.github.hapjava.characteristics.HomekitCharacteristicChangeCallback;
import io.github.hapjava.characteristics.impl.base.EnumCharacteristic;
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
        RotationDirectionEnum.values(),
        Optional.of(getter),
        Optional.of(setter),
        Optional.of(subscriber),
        Optional.of(unsubscriber));
  }
}
