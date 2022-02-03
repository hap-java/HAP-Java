package io.github.hapjava.characteristics.impl.inputsource;

import io.github.hapjava.characteristics.ExceptionalConsumer;
import io.github.hapjava.characteristics.HomekitCharacteristicChangeCallback;
import io.github.hapjava.characteristics.impl.base.EnumCharacteristic;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import java.util.function.Supplier;

/** This characteristic describes target visibility state. */
public class TargetVisibilityStateCharacteristic
    extends EnumCharacteristic<TargetVisibilityStateEnum> {

  public TargetVisibilityStateCharacteristic(
      Supplier<CompletableFuture<TargetVisibilityStateEnum>> getter,
      ExceptionalConsumer<TargetVisibilityStateEnum> setter,
      Consumer<HomekitCharacteristicChangeCallback> subscriber,
      Runnable unsubscriber) {
    super(
        "00000134-0000-1000-8000-0026BB765291",
        "target visibility state",
        TargetVisibilityStateEnum.values(),
        Optional.of(getter),
        Optional.of(setter),
        Optional.of(subscriber),
        Optional.of(unsubscriber));
  }
}
