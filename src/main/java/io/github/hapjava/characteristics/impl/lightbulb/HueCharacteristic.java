package io.github.hapjava.characteristics.impl.lightbulb;

import io.github.hapjava.characteristics.ExceptionalConsumer;
import io.github.hapjava.characteristics.HomekitCharacteristicChangeCallback;
import io.github.hapjava.characteristics.impl.base.FloatCharacteristic;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import java.util.function.Supplier;

/** This characteristic describes hue or color. */
public class HueCharacteristic extends FloatCharacteristic {
  public static final double DEFAULT_MIN_VALUE = 0;
  public static final double DEFAULT_MAX_VALUE = 360;
  public static final double DEFAULT_STEP = 1;

  public HueCharacteristic(
      double minValue,
      double maxValue,
      double minStep,
      Supplier<CompletableFuture<Double>> getter,
      ExceptionalConsumer<Double> setter,
      Consumer<HomekitCharacteristicChangeCallback> subscriber,
      Runnable unsubscriber) {
    super(
        "00000013-0000-1000-8000-0026BB765291",
        "hue or color",
        minValue,
        maxValue,
        minStep,
        "arcdegrees",
        Optional.of(getter),
        Optional.of(setter),
        Optional.of(subscriber),
        Optional.of(unsubscriber));
  }

  public HueCharacteristic(
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
