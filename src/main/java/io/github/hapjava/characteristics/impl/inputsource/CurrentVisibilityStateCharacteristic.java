package io.github.hapjava.characteristics.impl.inputsource;

import io.github.hapjava.characteristics.HomekitCharacteristicChangeCallback;
import io.github.hapjava.characteristics.impl.base.EnumCharacteristic;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import java.util.function.Supplier;

/** This characteristic describes current visibility state. */
public class CurrentVisibilityStateCharacteristic
    extends EnumCharacteristic<CurrentVisibilityStateEnum> {

  public CurrentVisibilityStateCharacteristic(
      Supplier<CompletableFuture<CurrentVisibilityStateEnum>> getter,
      Consumer<HomekitCharacteristicChangeCallback> subscriber,
      Runnable unsubscriber) {
    super(
        "00000135-0000-1000-8000-0026BB765291",
        "current visibility state",
        CurrentVisibilityStateEnum.values(),
        Optional.of(getter),
        Optional.empty(),
        Optional.of(subscriber),
        Optional.of(unsubscriber));
  }
}
