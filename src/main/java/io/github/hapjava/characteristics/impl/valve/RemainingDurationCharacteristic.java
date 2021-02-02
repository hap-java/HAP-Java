package io.github.hapjava.characteristics.impl.valve;

import io.github.hapjava.characteristics.EventableCharacteristic;
import io.github.hapjava.characteristics.HomekitCharacteristicChangeCallback;
import io.github.hapjava.characteristics.impl.base.IntegerCharacteristic;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * This characteristic describes the remaining duration of the active status of an accessory, e.g.
 * {@link io.github.hapjava.accessories.ValveAccessory}. This characteristic should only notify
 * about changes if it defers from normal countdown.
 */
public class RemainingDurationCharacteristic extends IntegerCharacteristic
    implements EventableCharacteristic {
  public static final int DEFAULT_MIN_VALUE = 0;
  public static final int DEFAULT_MAX_VALUE = 3600;

  public RemainingDurationCharacteristic(
      int minValue,
      int maxValue,
      Supplier<CompletableFuture<Integer>> getter,
      Consumer<HomekitCharacteristicChangeCallback> subscriber,
      Runnable unsubscriber) {
    super(
        "000000D4-0000-1000-8000-0026BB765291",
        "remaining duration",
        minValue,
        maxValue,
        "s",
        Optional.of(getter),
        Optional.empty(),
        Optional.of(subscriber),
        Optional.of(unsubscriber));
  }

  public RemainingDurationCharacteristic(
      Supplier<CompletableFuture<Integer>> getter,
      Consumer<HomekitCharacteristicChangeCallback> subscriber,
      Runnable unsubscriber) {
    this(DEFAULT_MIN_VALUE, DEFAULT_MAX_VALUE, getter, subscriber, unsubscriber);
  }
}
