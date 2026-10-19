package io.github.hapjava.characteristics.impl.common;

import io.github.hapjava.characteristics.EventableCharacteristic;
import io.github.hapjava.characteristics.HomekitCharacteristicChangeCallback;
import io.github.hapjava.characteristics.impl.base.FloatCharacteristic;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import java.util.function.Supplier;

/** This characteristic describes the water level. */
public class WaterLavelCharacteristic extends FloatCharacteristic
    implements EventableCharacteristic {

  public WaterLavelCharacteristic(
      Supplier<CompletableFuture<Double>> getter,
      Consumer<HomekitCharacteristicChangeCallback> subscriber,
      Runnable unsubscriber) {
    super(
        "000000B5-0000-1000-8000-0026BB765291",
        "water level",
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
