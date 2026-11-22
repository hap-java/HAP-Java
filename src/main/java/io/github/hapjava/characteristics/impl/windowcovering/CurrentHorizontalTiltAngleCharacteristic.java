package io.github.hapjava.characteristics.impl.windowcovering;

import io.github.hapjava.characteristics.EventableCharacteristic;
import io.github.hapjava.characteristics.HomekitCharacteristicChangeCallback;
import io.github.hapjava.characteristics.impl.base.IntegerCharacteristic;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import java.util.function.Supplier;

/** This characteristic describes the current angle of horizontal slats. */
public class CurrentHorizontalTiltAngleCharacteristic extends IntegerCharacteristic
    implements EventableCharacteristic {
  public static final int DEFAULT_MIN_VALUE = -90;
  public static final int DEFAULT_MAX_VALUE = 90;

  public CurrentHorizontalTiltAngleCharacteristic(
      int minValue,
      int maxValue,
      Supplier<CompletableFuture<Integer>> getter,
      Consumer<HomekitCharacteristicChangeCallback> subscriber,
      Runnable unsubscriber) {
    super(
        "0000006C-0000-1000-8000-0026BB765291",
        "current horizontal tilt angle",
        minValue,
        maxValue,
        "arcdegrees",
        Optional.of(getter),
        Optional.empty(),
        Optional.of(subscriber),
        Optional.of(unsubscriber));
  }

  public CurrentHorizontalTiltAngleCharacteristic(
      Supplier<CompletableFuture<Integer>> getter,
      Consumer<HomekitCharacteristicChangeCallback> subscriber,
      Runnable unsubscriber) {
    this(DEFAULT_MIN_VALUE, DEFAULT_MAX_VALUE, getter, subscriber, unsubscriber);
  }
}
