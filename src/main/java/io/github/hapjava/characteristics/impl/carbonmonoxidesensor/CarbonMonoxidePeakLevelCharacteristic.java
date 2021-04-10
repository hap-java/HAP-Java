package io.github.hapjava.characteristics.impl.carbonmonoxidesensor;

import io.github.hapjava.characteristics.HomekitCharacteristicChangeCallback;
import io.github.hapjava.characteristics.impl.base.FloatCharacteristic;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import java.util.function.Supplier;

/** This characteristic indicates the highest detected level (ppm) of Carbon Monoxide. */
public class CarbonMonoxidePeakLevelCharacteristic extends FloatCharacteristic {
  public static final double DEFAULT_MIN_VALUE = 0;
  public static final double DEFAULT_MAX_VALUE = 100;
  public static final double DEFAULT_STEP = 1;

  public CarbonMonoxidePeakLevelCharacteristic(
      double minValue,
      double maxValue,
      double minStep,
      Supplier<CompletableFuture<Double>> getter,
      Consumer<HomekitCharacteristicChangeCallback> subscriber,
      Runnable unsubscriber) {
    super(
        "00000091-0000-1000-8000-0026BB765291",
        "Carbon Monoxide Peak Level",
        minValue,
        maxValue,
        minStep,
        "ppm",
        Optional.of(getter),
        Optional.empty(),
        Optional.of(subscriber),
        Optional.of(unsubscriber));
  }

  public CarbonMonoxidePeakLevelCharacteristic(
      Supplier<CompletableFuture<Double>> getter,
      Consumer<HomekitCharacteristicChangeCallback> subscriber,
      Runnable unsubscriber) {
    this(DEFAULT_MIN_VALUE, DEFAULT_MAX_VALUE, DEFAULT_STEP, getter, subscriber, unsubscriber);
  }
}
