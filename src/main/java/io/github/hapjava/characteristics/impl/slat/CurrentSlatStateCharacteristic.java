package io.github.hapjava.characteristics.impl.slat;

import io.github.hapjava.characteristics.HomekitCharacteristicChangeCallback;
import io.github.hapjava.characteristics.impl.base.EnumCharacteristic;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import java.util.function.Supplier;

/** This characteristic describes the current state of the slats. */
public class CurrentSlatStateCharacteristic extends EnumCharacteristic<CurrentSlatStateEnum> {

  public CurrentSlatStateCharacteristic(
      Supplier<CompletableFuture<CurrentSlatStateEnum>> getter,
      Consumer<HomekitCharacteristicChangeCallback> subscriber,
      Runnable unsubscriber) {
    super(
        "000000AA-0000-1000-8000-0026BB765291",
        "current slat state",
        CurrentSlatStateEnum.values(),
        Optional.of(getter),
        Optional.empty(),
        Optional.of(subscriber),
        Optional.of(unsubscriber));
  }
}
