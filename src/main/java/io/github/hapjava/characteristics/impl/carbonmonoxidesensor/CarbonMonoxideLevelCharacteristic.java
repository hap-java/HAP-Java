package io.github.hapjava.characteristics.impl.carbonmonoxidesensor;

import io.github.hapjava.characteristics.HomekitCharacteristicChangeCallback;
import io.github.hapjava.characteristics.impl.base.FloatCharacteristic;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import java.util.function.Supplier;

/** This characteristic indicates the Carbon Monoxide levels detected in parts per million (ppm). */
public class CarbonMonoxideLevelCharacteristic extends FloatCharacteristic {

  public CarbonMonoxideLevelCharacteristic(
      Supplier<CompletableFuture<Double>> getter,
      Consumer<HomekitCharacteristicChangeCallback> subscriber,
      Runnable unsubscriber) {
    super(
        "00000090-0000-1000-8000-0026BB765291",
        "Carbon Monoxide Level",
        0,
        100,
        1,
        "ppm",
        Optional.of(getter),
        Optional.empty(),
        Optional.of(subscriber),
        Optional.of(unsubscriber));
  }
}
