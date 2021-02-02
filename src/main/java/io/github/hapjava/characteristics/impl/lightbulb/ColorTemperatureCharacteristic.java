package io.github.hapjava.characteristics.impl.lightbulb;

import io.github.hapjava.characteristics.EventableCharacteristic;
import io.github.hapjava.characteristics.ExceptionalConsumer;
import io.github.hapjava.characteristics.HomekitCharacteristicChangeCallback;
import io.github.hapjava.characteristics.impl.base.IntegerCharacteristic;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import java.util.function.Supplier;

/** This characteristic describes color temperature in Kelvin */
public class ColorTemperatureCharacteristic extends IntegerCharacteristic
    implements EventableCharacteristic {
  public static final int DEFAULT_MIN_VALUE = 50;
  public static final int DEFAULT_MAX_VALUE = 400;

  public ColorTemperatureCharacteristic(
      int minValue,
      int maxValue,
      Supplier<CompletableFuture<Integer>> getter,
      ExceptionalConsumer<Integer> setter,
      Consumer<HomekitCharacteristicChangeCallback> subscriber,
      Runnable unsubscriber) {
    super(
        "000000CE-0000-1000-8000-0026BB765291",
        "color temperature",
        minValue,
        maxValue,
        "K",
        Optional.of(getter),
        Optional.of(setter),
        Optional.of(subscriber),
        Optional.of(unsubscriber));
  }

  public ColorTemperatureCharacteristic(
      Supplier<CompletableFuture<Integer>> getter,
      ExceptionalConsumer<Integer> setter,
      Consumer<HomekitCharacteristicChangeCallback> subscriber,
      Runnable unsubscriber) {
    this(DEFAULT_MIN_VALUE, DEFAULT_MAX_VALUE, getter, setter, subscriber, unsubscriber);
  }
}
