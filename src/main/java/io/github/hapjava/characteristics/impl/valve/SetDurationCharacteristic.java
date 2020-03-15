package io.github.hapjava.characteristics.impl.valve;

import io.github.hapjava.characteristics.EventableCharacteristic;
import io.github.hapjava.characteristics.ExceptionalConsumer;
import io.github.hapjava.characteristics.HomekitCharacteristicChangeCallback;
import io.github.hapjava.characteristics.impl.base.IntegerCharacteristic;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * This characteristic describes the set duration. For a Valve” this duration defines how long a
 * valve should be set to ʼIn Useʼ. Once the valve is ʼIn Useʼ, any changes to this characteristic
 * take affect in the next operation when the Valve is Active. This duration is defined in seconds.
 */
public class SetDurationCharacteristic extends IntegerCharacteristic
    implements EventableCharacteristic {

  public SetDurationCharacteristic(
      Supplier<CompletableFuture<Integer>> getter,
      ExceptionalConsumer<Integer> setter,
      Consumer<HomekitCharacteristicChangeCallback> subscriber,
      Runnable unsubscriber) {
    super(
        "000000D3-0000-1000-8000-0026BB765291",
        "set duration",
        0,
        3600,
        "s",
        Optional.of(getter),
        Optional.of(setter),
        Optional.of(subscriber),
        Optional.of(unsubscriber));
  }
}
