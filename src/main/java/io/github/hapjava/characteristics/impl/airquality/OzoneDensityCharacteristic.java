package io.github.hapjava.characteristics.impl.airquality;

import io.github.hapjava.characteristics.HomekitCharacteristicChangeCallback;
import io.github.hapjava.characteristics.impl.base.FloatCharacteristic;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import java.util.function.Supplier;

/** This characteristic contains the current ozone density in micrograms/m3. */
public class OzoneDensityCharacteristic extends FloatCharacteristic {
  public static final double DEFAULT_MIN_VALUE = 0;
  public static final double DEFAULT_MAX_VALUE = 1000;
  public static final double DEFAULT_STEP = 1;

  public OzoneDensityCharacteristic(
      double minValue,
      double maxValue,
      double minStep,
      Supplier<CompletableFuture<Double>> getter,
      Consumer<HomekitCharacteristicChangeCallback> subscriber,
      Runnable unsubscriber) {
    super(
        "000000C3-0000-1000-8000-0026BB765291",
        "ozone density",
        minValue,
        maxValue,
        minStep,
        "micrograms",
        Optional.of(getter),
        Optional.empty(),
        Optional.of(subscriber),
        Optional.of(unsubscriber));
  }

  public OzoneDensityCharacteristic(
      Supplier<CompletableFuture<Double>> getter,
      Consumer<HomekitCharacteristicChangeCallback> subscriber,
      Runnable unsubscriber) {
    this(DEFAULT_MIN_VALUE, DEFAULT_MAX_VALUE, DEFAULT_STEP, getter, subscriber, unsubscriber);
  }
}
