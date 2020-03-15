package io.github.hapjava.characteristics.impl.common;

import io.github.hapjava.characteristics.impl.base.EnumCharacteristic;
import io.github.hapjava.characteristics.HomekitCharacteristicChangeCallback;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * This characteristic describes an accessory which has been tampered with. A status of 1 indicates
 * that the accessory has been tampered with. Value should return to 0 when the accessory has been
 * reset to a non-tampered state.
 */
public class StatusTamperedCharacteristic extends EnumCharacteristic<StatusTamperedEnum> {

  public StatusTamperedCharacteristic(
      Supplier<CompletableFuture<StatusTamperedEnum>> getter,
      Consumer<HomekitCharacteristicChangeCallback> subscriber,
      Runnable unsubscriber) {
    super(
        "0000007A-0000-1000-8000-0026BB765291",
        "Status Tampered",
        1,
        Optional.of(getter),
        Optional.empty(),
        Optional.of(subscriber),
        Optional.of(unsubscriber));
  }
}
