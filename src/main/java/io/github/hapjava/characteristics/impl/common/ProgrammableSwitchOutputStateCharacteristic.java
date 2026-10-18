package io.github.hapjava.characteristics.impl.common;

import io.github.hapjava.characteristics.EventableCharacteristic;
import io.github.hapjava.characteristics.ExceptionalConsumer;
import io.github.hapjava.characteristics.HomekitCharacteristicChangeCallback;
import io.github.hapjava.characteristics.impl.base.IntegerCharacteristic;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * This characteristic describes the output state of a stateful programmable switch, e.g. {@link
 * io.github.hapjava.accessories.StatefulProgrammableSwitchAccessory}. Valid values are 0 and 1.
 */
public class ProgrammableSwitchOutputStateCharacteristic extends IntegerCharacteristic
    implements EventableCharacteristic {
  public static final int MIN_VALUE = 0;
  public static final int MAX_VALUE = 1;

  public ProgrammableSwitchOutputStateCharacteristic(
      Supplier<CompletableFuture<Integer>> getter,
      ExceptionalConsumer<Integer> setter,
      Consumer<HomekitCharacteristicChangeCallback> subscriber,
      Runnable unsubscriber) {
    super(
        "00000074-0000-1000-8000-0026BB765291",
        "uint8",
        "programmable switch output state",
        MIN_VALUE,
        MAX_VALUE,
        null,
        Optional.of(getter),
        Optional.of(setter),
        Optional.of(subscriber),
        Optional.of(unsubscriber));
  }
}
