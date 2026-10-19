package io.github.hapjava.characteristics.impl.humiditysensor;

import io.github.hapjava.characteristics.ExceptionalConsumer;
import io.github.hapjava.characteristics.HomekitCharacteristicChangeCallback;
import io.github.hapjava.characteristics.impl.base.FloatCharacteristic;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import java.util.function.Supplier;

/** This characteristic describes the target relative humidity. */
public class TargetRelativeHumidityCharacteristic extends FloatCharacteristic {

  public TargetRelativeHumidityCharacteristic(
      Supplier<CompletableFuture<Double>> getter,
      ExceptionalConsumer<Double> setter,
      Consumer<HomekitCharacteristicChangeCallback> subscriber,
      Runnable unsubscriber) {
    super(
        "00000034-0000-1000-8000-0026BB765291",
        "Target Relative Humidity",
        0,
        100,
        1,
        "%",
        Optional.of(getter),
        Optional.of(setter),
        Optional.of(subscriber),
        Optional.of(unsubscriber));
  }
}
