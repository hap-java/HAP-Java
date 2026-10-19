package io.github.hapjava.characteristics.impl.humiditysensor;

import io.github.hapjava.characteristics.HomekitCharacteristicChangeCallback;
import io.github.hapjava.characteristics.impl.base.FloatCharacteristic;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import java.util.function.Supplier;

/** This characteristic describes the current relative humidity. */
public class CurrentRelativeHumidityCharacteristic extends FloatCharacteristic {

  public CurrentRelativeHumidityCharacteristic(
      Supplier<CompletableFuture<Double>> getter,
      Consumer<HomekitCharacteristicChangeCallback> subscriber,
      Runnable unsubscriber) {
    super(
        "00000010-0000-1000-8000-0026BB765291",
        "Current Relative Humidity",
        0,
        100,
        1,
        "%",
        Optional.of(getter),
        Optional.empty(),
        Optional.of(subscriber),
        Optional.of(unsubscriber));
  }
}
