package io.github.hapjava.characteristics.impl.valve;

import io.github.hapjava.characteristics.HomekitCharacteristicChangeCallback;
import io.github.hapjava.characteristics.impl.base.EnumCharacteristic;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import java.util.function.Supplier;

/** This characteristic describes the type of valve. */
public class ValveTypeCharacteristic extends EnumCharacteristic<ValveTypeEnum> {

  public ValveTypeCharacteristic(
      Supplier<CompletableFuture<ValveTypeEnum>> getter,
      Consumer<HomekitCharacteristicChangeCallback> subscriber,
      Runnable unsubscriber) {
    super(
        "000000D5-0000-1000-8000-0026BB765291",
        "Valve type",
        ValveTypeEnum.values(),
        Optional.of(getter),
        Optional.empty(),
        Optional.of(subscriber),
        Optional.of(unsubscriber));
  }
}
