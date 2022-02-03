package io.github.hapjava.characteristics.impl.television;

import io.github.hapjava.characteristics.HomekitCharacteristicChangeCallback;
import io.github.hapjava.characteristics.impl.base.EnumCharacteristic;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * This characteristic indicates current media state. See {@link CurrentMediaStateEnum} for possible
 * values.
 */
public class CurrentMediaStateCharacteristic extends EnumCharacteristic<CurrentMediaStateEnum> {
  public CurrentMediaStateCharacteristic(
      Supplier<CompletableFuture<CurrentMediaStateEnum>> getter,
      Consumer<HomekitCharacteristicChangeCallback> subscriber,
      Runnable unsubscriber) {
    super(
        "000000E0-0000-1000-8000-0026BB765291",
        "current media state",
        CurrentMediaStateEnum.values(),
        Optional.of(getter),
        Optional.empty(),
        Optional.of(subscriber),
        Optional.of(unsubscriber));
  }
}
