package io.github.hapjava.characteristics.impl.fan;

import io.github.hapjava.characteristics.EventableCharacteristic;
import io.github.hapjava.characteristics.ExceptionalConsumer;
import io.github.hapjava.characteristics.HomekitCharacteristicChangeCallback;
import io.github.hapjava.characteristics.impl.base.FloatCharacteristic;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import java.util.function.Supplier;

/** This characteristic describes the rotation speed of a fan. */
public class RotationSpeedCharacteristic extends FloatCharacteristic
    implements EventableCharacteristic {
  public static final double DEFAULT_MIN_VALUE = 0;
  public static final double DEFAULT_MAX_VALUE = 100;
  public static final double DEFAULT_STEP = 1;

  public RotationSpeedCharacteristic(
      double minValue,
      double maxValue,
      double minStep,
      Supplier<CompletableFuture<Double>> getter,
      ExceptionalConsumer<Double> setter,
      Consumer<HomekitCharacteristicChangeCallback> subscriber,
      Runnable unsubscriber) {
    super(
        "00000029-0000-1000-8000-0026BB765291",
        "Rotation Speed",
        minValue,
        maxValue,
        minStep,
        "%",
        Optional.of(getter),
        Optional.of(setter),
        Optional.of(subscriber),
        Optional.of(unsubscriber));
  }

  public RotationSpeedCharacteristic(
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
