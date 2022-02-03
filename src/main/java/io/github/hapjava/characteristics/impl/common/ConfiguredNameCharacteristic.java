package io.github.hapjava.characteristics.impl.common;

import io.github.hapjava.characteristics.ExceptionalConsumer;
import io.github.hapjava.characteristics.HomekitCharacteristicChangeCallback;
import io.github.hapjava.characteristics.impl.base.StringCharacteristic;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import java.util.function.Supplier;

/** This characteristic describes a configured name. */
public class ConfiguredNameCharacteristic extends StringCharacteristic {

  public ConfiguredNameCharacteristic(
      Supplier<CompletableFuture<String>> getter,
      ExceptionalConsumer<String> setter,
      Consumer<HomekitCharacteristicChangeCallback> subscriber,
      Runnable unsubscriber) {
    super(
        "000000E3-0000-1000-8000-0026BB765291",
        "configured name",
        Optional.of(getter),
        Optional.of(setter),
        Optional.of(subscriber),
        Optional.of(unsubscriber));
  }
}
