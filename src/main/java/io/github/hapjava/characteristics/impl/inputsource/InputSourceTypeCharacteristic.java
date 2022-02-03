package io.github.hapjava.characteristics.impl.inputsource;

import io.github.hapjava.characteristics.HomekitCharacteristicChangeCallback;
import io.github.hapjava.characteristics.impl.base.EnumCharacteristic;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import java.util.function.Supplier;

/** This characteristic describes input source type. */
public class InputSourceTypeCharacteristic extends EnumCharacteristic<InputSourceTypeEnum> {

  public InputSourceTypeCharacteristic(
      Supplier<CompletableFuture<InputSourceTypeEnum>> getter,
      Consumer<HomekitCharacteristicChangeCallback> subscriber,
      Runnable unsubscriber) {
    super(
        "000000DB-0000-1000-8000-0026BB765291",
        "input source type",
        InputSourceTypeEnum.values(),
        Optional.of(getter),
        Optional.empty(),
        Optional.of(subscriber),
        Optional.of(unsubscriber));
  }
}
