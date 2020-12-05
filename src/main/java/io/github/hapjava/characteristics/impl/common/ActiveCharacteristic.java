package io.github.hapjava.characteristics.impl.common;

import io.github.hapjava.characteristics.ExceptionalConsumer;
import io.github.hapjava.characteristics.HomekitCharacteristicChangeCallback;
import io.github.hapjava.characteristics.impl.base.EnumCharacteristic;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * Active characteristic indicates whether the service is currently active. See {@link ActiveEnum}
 * for possible values.
 */
public class ActiveCharacteristic extends EnumCharacteristic<ActiveEnum> {

  public ActiveCharacteristic(
      Supplier<CompletableFuture<ActiveEnum>> getter,
      ExceptionalConsumer<ActiveEnum> setter,
      Consumer<HomekitCharacteristicChangeCallback> subscriber,
      Runnable unsubscriber) {
    super(
        "000000B0-0000-1000-8000-0026BB765291",
        "Active",
        ActiveEnum.values(),
        Optional.of(getter),
        Optional.of(setter),
        Optional.of(subscriber),
        Optional.of(unsubscriber));
  }
}
