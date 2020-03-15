package io.github.hapjava.characteristics.impl.common;

import io.github.hapjava.characteristics.impl.base.EnumCharacteristic;
import io.github.hapjava.characteristics.HomekitCharacteristicChangeCallback;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * This characteristic describes if the service is in use. The service must be Active before the
 * value of this characteristic can be set to in use.
 */
public class InUseCharacteristic extends EnumCharacteristic<InUseEnum> {
  public InUseCharacteristic(
      Supplier<CompletableFuture<InUseEnum>> getter,
      Consumer<HomekitCharacteristicChangeCallback> subscriber,
      Runnable unsubscriber) {
    super(
        "000000D2-0000-1000-8000-0026BB765291",
        "In Use",
        1,
        Optional.of(getter),
        Optional.empty(),
        Optional.of(subscriber),
        Optional.of(unsubscriber));
  }
}
