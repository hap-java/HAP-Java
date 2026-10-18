package io.github.hapjava.characteristics.impl.battery;

import io.github.hapjava.characteristics.EventableCharacteristic;
import io.github.hapjava.characteristics.HomekitCharacteristicChangeCallback;
import io.github.hapjava.characteristics.impl.base.IntegerCharacteristic;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import java.util.function.Supplier;

/** This characteristic describes the current level of the battery. */
public class BatteryLevelCharacteristic extends IntegerCharacteristic
    implements EventableCharacteristic {
  public static final int DEFAULT_MIN_VALUE = 0;
  public static final int DEFAULT_MAX_VALUE = 100;

  public BatteryLevelCharacteristic(
      int minValue,
      int maxValue,
      Supplier<CompletableFuture<Integer>> getter,
      Consumer<HomekitCharacteristicChangeCallback> subscriber,
      Runnable unsubscriber) {
    super(
        "00000068-0000-1000-8000-0026BB765291",
        "battery level",
        minValue,
        maxValue,
        "%",
        Optional.of(getter),
        Optional.empty(),
        Optional.of(subscriber),
        Optional.of(unsubscriber));
  }

  public BatteryLevelCharacteristic(
      Supplier<CompletableFuture<Integer>> getter,
      Consumer<HomekitCharacteristicChangeCallback> subscriber,
      Runnable unsubscriber) {
    this(DEFAULT_MIN_VALUE, DEFAULT_MAX_VALUE, getter, subscriber, unsubscriber);
  }
}
