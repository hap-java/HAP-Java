package io.github.hapjava.characteristics.impl.airquality;

import io.github.hapjava.characteristics.HomekitCharacteristicChangeCallback;
import io.github.hapjava.characteristics.impl.base.FloatCharacteristic;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * This characteristic contains the current PM2.5 micrometer particulate density in micrograms/m3.
 */
public class PM25DensityCharacteristic extends FloatCharacteristic {
  public static final double DEFAULT_MIN_VALUE = 0;
  public static final double DEFAULT_MAX_VALUE = 1000;
  public static final double DEFAULT_STEP = 1;

  public PM25DensityCharacteristic(
      double minValue,
      double maxValue,
      double minStep,
      Supplier<CompletableFuture<Double>> getter,
      Consumer<HomekitCharacteristicChangeCallback> subscriber,
      Runnable unsubscriber) {
    super(
        "000000C6-0000-1000-8000-0026BB765291",
        "PM2.5 density",
        minValue,
        maxValue,
        minStep,
        "micrograms",
        Optional.of(getter),
        Optional.empty(),
        Optional.of(subscriber),
        Optional.of(unsubscriber));
  }

  public PM25DensityCharacteristic(
      Supplier<CompletableFuture<Double>> getter,
      Consumer<HomekitCharacteristicChangeCallback> subscriber,
      Runnable unsubscriber) {
    this(DEFAULT_MIN_VALUE, DEFAULT_MAX_VALUE, DEFAULT_STEP, getter, subscriber, unsubscriber);
  }
}
