package io.github.hapjava.characteristics.impl.thermostat;

import io.github.hapjava.characteristics.ExceptionalConsumer;
import io.github.hapjava.characteristics.HomekitCharacteristicChangeCallback;
import io.github.hapjava.characteristics.impl.base.FloatCharacteristic;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * This characteristic describes the target temperature in Celsius that the accessory is actively
 * attempting to reach.
 */
public class TargetTemperatureCharacteristic extends FloatCharacteristic {
  public static final double DEFAULT_MIN_VALUE = 10;
  public static final double DEFAULT_MAX_VALUE = 38;
  public static final double DEFAULT_STEP = 0.1;

  public TargetTemperatureCharacteristic(
      double minValue,
      double maxValue,
      double minStep,
      Supplier<CompletableFuture<Double>> getter,
      ExceptionalConsumer<Double> setter,
      Consumer<HomekitCharacteristicChangeCallback> subscriber,
      Runnable unsubscriber) {
    super(
        "00000035-0000-1000-8000-0026BB765291",
        "target temperature",
        minValue,
        maxValue,
        minStep,
        "C",
        Optional.of(getter),
        Optional.of(setter),
        Optional.of(subscriber),
        Optional.of(unsubscriber));
  }

  public TargetTemperatureCharacteristic(
      Supplier<CompletableFuture<Double>> getter,
      ExceptionalConsumer<Double> setter,
      Consumer<HomekitCharacteristicChangeCallback> subscriber,
      Runnable unsubscriber) {
    this(
        DEFAULT_MIN_VALUE,
        DEFAULT_MAX_VALUE,
        DEFAULT_STEP,
        getter,
        setter,
        subscriber,
        unsubscriber);
  }
}
