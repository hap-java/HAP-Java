package io.github.hapjava.characteristics.impl.common;

import io.github.hapjava.characteristics.HomekitCharacteristicChangeCallback;
import io.github.hapjava.characteristics.impl.base.EnumCharacteristic;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * This characteristic describes if there are programs scheduled on the accessory. See {@link
 * ProgramModeEnum} for possible values.
 */
public class ProgramModeCharacteristic extends EnumCharacteristic<ProgramModeEnum> {
  public ProgramModeCharacteristic(
      Supplier<CompletableFuture<ProgramModeEnum>> getter,
      Consumer<HomekitCharacteristicChangeCallback> subscriber,
      Runnable unsubscriber) {
    super(
        "000000D1-0000-1000-8000-0026BB765291",
        "Program Mode",
        ProgramModeEnum.values(),
        Optional.of(getter),
        Optional.empty(),
        Optional.of(subscriber),
        Optional.of(unsubscriber));
  }
}
