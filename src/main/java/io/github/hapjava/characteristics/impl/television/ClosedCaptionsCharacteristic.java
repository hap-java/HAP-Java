package io.github.hapjava.characteristics.impl.television;

import io.github.hapjava.characteristics.ExceptionalConsumer;
import io.github.hapjava.characteristics.HomekitCharacteristicChangeCallback;
import io.github.hapjava.characteristics.impl.base.EnumCharacteristic;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * Characteristic indicates whether the service provides closed captions. See {@link
 * ClosedCaptionsEnum} for possible values.
 */
public class ClosedCaptionsCharacteristic extends EnumCharacteristic<ClosedCaptionsEnum> {

  public ClosedCaptionsCharacteristic(
      Supplier<CompletableFuture<ClosedCaptionsEnum>> getter,
      ExceptionalConsumer<ClosedCaptionsEnum> setter,
      Consumer<HomekitCharacteristicChangeCallback> subscriber,
      Runnable unsubscriber) {
    super(
        "000000DD-0000-1000-8000-0026BB765291",
        "closed captions",
        ClosedCaptionsEnum.values(),
        Optional.of(getter),
        Optional.of(setter),
        Optional.of(subscriber),
        Optional.of(unsubscriber));
  }
}
