package io.github.hapjava.characteristics.impl.lightsensor;

import io.github.hapjava.characteristics.HomekitCharacteristicChangeCallback;
import io.github.hapjava.characteristics.impl.base.FloatCharacteristic;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import java.util.function.Supplier;

/** This characteristic indicates the current light level in Lux */
public class CurrentAmbientLightLevelCharacteristic extends FloatCharacteristic {
  public static final double DEFAULT_MIN_VALUE = 0.0001;
  public static final double DEFAULT_MAX_VALUE = 100000;
  public static final double DEFAULT_STEP = 0.0001;

  public CurrentAmbientLightLevelCharacteristic(
      double minValue,
      double maxValue,
      double minStep,
      Supplier<CompletableFuture<Double>> getter,
      Consumer<HomekitCharacteristicChangeCallback> subscriber,
      Runnable unsubscriber) {
    super(
        "0000006B-0000-1000-8000-0026BB765291",
        "ambient light level",
        minValue,
        maxValue,
        minStep,
        "lux",
        Optional.of(getter),
        Optional.empty(),
        Optional.of(subscriber),
        Optional.of(unsubscriber));
  }

  public CurrentAmbientLightLevelCharacteristic(
      Supplier<CompletableFuture<Double>> getter,
      Consumer<HomekitCharacteristicChangeCallback> subscriber,
      Runnable unsubscriber) {
    this(DEFAULT_MIN_VALUE, DEFAULT_MAX_VALUE, DEFAULT_STEP, getter, subscriber, unsubscriber);
  }
}
