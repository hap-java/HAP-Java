package io.github.hapjava.characteristics.impl.smokesensor;

import io.github.hapjava.characteristics.HomekitCharacteristicChangeCallback;
import io.github.hapjava.characteristics.impl.base.EnumCharacteristic;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import java.util.function.Supplier;

/** This characteristic indicates if a sensor detects smoke. */
public class SmokeDetectedCharacteristic extends EnumCharacteristic<SmokeDetectedStateEnum> {

  public SmokeDetectedCharacteristic(
      Supplier<CompletableFuture<SmokeDetectedStateEnum>> getter,
      Consumer<HomekitCharacteristicChangeCallback> subscriber,
      Runnable unsubscriber) {
    super(
        "00000076-0000-1000-8000-0026BB765291",
        "Smoke Detected",
        SmokeDetectedStateEnum.values(),
        Optional.of(getter),
        Optional.empty(),
        Optional.of(subscriber),
        Optional.of(unsubscriber));
  }
}
