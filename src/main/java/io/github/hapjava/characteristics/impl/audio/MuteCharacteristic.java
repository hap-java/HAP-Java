package io.github.hapjava.characteristics.impl.audio;

import io.github.hapjava.characteristics.ExceptionalConsumer;
import io.github.hapjava.characteristics.HomekitCharacteristicChangeCallback;
import io.github.hapjava.characteristics.impl.base.BooleanCharacteristic;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import java.util.function.Supplier;

/** Mute characteristic to control audio input or output accessory. */
public class MuteCharacteristic extends BooleanCharacteristic {
  public MuteCharacteristic(
      Supplier<CompletableFuture<Boolean>> getter,
      ExceptionalConsumer<Boolean> setter,
      Consumer<HomekitCharacteristicChangeCallback> subscriber,
      Runnable unsubscriber) {
    super(
        "0000011A-0000-1000-8000-0026BB765291",
        "Mute",
        Optional.of(getter),
        Optional.of(setter),
        Optional.of(subscriber),
        Optional.of(unsubscriber));
  }
}
