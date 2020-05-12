package io.github.hapjava.characteristics.impl.common;

import io.github.hapjava.characteristics.HomekitCharacteristicChangeCallback;
import io.github.hapjava.characteristics.impl.base.EnumCharacteristic;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import java.util.function.Supplier;

/** This characteristic describes fault status. See {@link StatusFaultEnum} for possible values. */
public class StatusFaultCharacteristic extends EnumCharacteristic<StatusFaultEnum> {

  public StatusFaultCharacteristic(
      Supplier<CompletableFuture<StatusFaultEnum>> getter,
      Consumer<HomekitCharacteristicChangeCallback> subscriber,
      Runnable unsubscriber) {
    super(
        "00000077-0000-1000-8000-0026BB765291",
        "Status Fault",
        1,
        Optional.of(getter),
        Optional.empty(),
        Optional.of(subscriber),
        Optional.of(unsubscriber));
  }
}
