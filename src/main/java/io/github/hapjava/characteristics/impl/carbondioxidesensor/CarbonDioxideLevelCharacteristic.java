package io.github.hapjava.characteristics.impl.carbondioxidesensor;

import io.github.hapjava.characteristics.HomekitCharacteristicChangeCallback;
import io.github.hapjava.characteristics.impl.base.FloatCharacteristic;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * This characteristic indicates the detected level of Carbon Dioxide in parts per million (ppm).
 */
public class CarbonDioxideLevelCharacteristic extends FloatCharacteristic {

  public CarbonDioxideLevelCharacteristic(
      Supplier<CompletableFuture<Double>> getter,
      Consumer<HomekitCharacteristicChangeCallback> subscriber,
      Runnable unsubscriber) {
    super(
        "00000093-0000-1000-8000-0026BB765291",
        "Carbon Dioxide Level",
        0,
        100000,
        1,
        "ppm",
        Optional.of(getter),
        Optional.empty(),
        Optional.of(subscriber),
        Optional.of(unsubscriber));
  }
}
