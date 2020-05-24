package io.github.hapjava.characteristics.impl.thermostat;

import io.github.hapjava.characteristics.HomekitCharacteristicChangeCallback;
import io.github.hapjava.characteristics.impl.base.FloatCharacteristic;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import java.util.function.Supplier;

/** characteristic describes the current temperature of the environment in Celsius */
public class CurrentTemperatureCharacteristic extends FloatCharacteristic {
  public static final double DEFAULT_MIN_VALUE = 0;
  public static final double DEFAULT_MAX_VALUE = 100;
  public static final double DEFAULT_STEP = 0.1;

  public CurrentTemperatureCharacteristic(
      double minValue,
      double maxValue,
      double minStep,
      Supplier<CompletableFuture<Double>> getter,
      Consumer<HomekitCharacteristicChangeCallback> subscriber,
      Runnable unsubscriber) {
    super(
        "00000011-0000-1000-8000-0026BB765291",
        "current temperature",
        minValue,
        maxValue,
        minStep,
        "C",
        Optional.of(getter),
        Optional.empty(),
        Optional.of(subscriber),
        Optional.of(unsubscriber));
  }

  public CurrentTemperatureCharacteristic(
      Supplier<CompletableFuture<Double>> getter,
      Consumer<HomekitCharacteristicChangeCallback> subscriber,
      Runnable unsubscriber) {
    this(DEFAULT_MIN_VALUE, DEFAULT_MAX_VALUE, DEFAULT_STEP, getter, subscriber, unsubscriber);
  }
}
