package io.github.hapjava.characteristics.impl.common;

import io.github.hapjava.characteristics.HomekitCharacteristicChangeCallback;
import io.github.hapjava.characteristics.impl.base.EnumCharacteristic;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * This characteristic describes tampered status. See {@link StatusTamperedEnum} for possible
 * values.
 */
public class StatusTamperedCharacteristic extends EnumCharacteristic<StatusTamperedEnum> {

  public StatusTamperedCharacteristic(
      Supplier<CompletableFuture<StatusTamperedEnum>> getter,
      Consumer<HomekitCharacteristicChangeCallback> subscriber,
      Runnable unsubscriber) {
    super(
        "0000007A-0000-1000-8000-0026BB765291",
        "Status Tampered",
        StatusTamperedEnum.values(),
        Optional.of(getter),
        Optional.empty(),
        Optional.of(subscriber),
        Optional.of(unsubscriber));
  }
}
