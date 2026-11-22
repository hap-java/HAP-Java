package io.github.hapjava.characteristics.impl.windowcovering;

import io.github.hapjava.characteristics.EventableCharacteristic;
import io.github.hapjava.characteristics.ExceptionalConsumer;
import io.github.hapjava.characteristics.HomekitCharacteristicChangeCallback;
import io.github.hapjava.characteristics.impl.base.IntegerCharacteristic;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import java.util.function.Supplier;

/** This characteristic describes the target angle of horizontal slats for accessories. */
public class TargetHorizontalTiltAngleCharacteristic extends IntegerCharacteristic
    implements EventableCharacteristic {
  public static final int DEFAULT_MIN_VALUE = -90;
  public static final int DEFAULT_MAX_VALUE = 90;

  public TargetHorizontalTiltAngleCharacteristic(
      int minValue,
      int maxValue,
      Supplier<CompletableFuture<Integer>> getter,
      ExceptionalConsumer<Integer> setter,
      Consumer<HomekitCharacteristicChangeCallback> subscriber,
      Runnable unsubscriber) {
    super(
        "0000007B-0000-1000-8000-0026BB765291",
        "target horizontal tilt angle",
        minValue,
        maxValue,
        "arcdegrees",
        Optional.of(getter),
        Optional.of(setter),
        Optional.of(subscriber),
        Optional.of(unsubscriber));
  }

  public TargetHorizontalTiltAngleCharacteristic(
      Supplier<CompletableFuture<Integer>> getter,
      ExceptionalConsumer<Integer> setter,
      Consumer<HomekitCharacteristicChangeCallback> subscriber,
      Runnable unsubscriber) {
    this(DEFAULT_MIN_VALUE, DEFAULT_MAX_VALUE, getter, setter, subscriber, unsubscriber);
  }
}
