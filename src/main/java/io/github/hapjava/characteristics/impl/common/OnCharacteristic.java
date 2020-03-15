package io.github.hapjava.characteristics.impl.common;

import io.github.hapjava.characteristics.impl.base.BooleanCharacteristic;
import io.github.hapjava.characteristics.ExceptionalConsumer;
import io.github.hapjava.characteristics.HomekitCharacteristicChangeCallback;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import java.util.function.Supplier;

/** This characteristic represents the states for “on” and “off”. */
public class OnCharacteristic extends BooleanCharacteristic {

  public OnCharacteristic(
      Supplier<CompletableFuture<Boolean>> getter,
      ExceptionalConsumer<Boolean> setter,
      Consumer<HomekitCharacteristicChangeCallback> subscriber,
      Runnable unsubscriber) {
    super(
        "00000025-0000-1000-8000-0026BB765291",
        "On / Off state",
        Optional.of(getter),
        Optional.of(setter),
        Optional.of(subscriber),
        Optional.of(unsubscriber));
  }
}
