package io.github.hapjava.characteristics.impl.thermostat;

import io.github.hapjava.characteristics.impl.base.FloatCharacteristic;
import io.github.hapjava.characteristics.HomekitCharacteristicChangeCallback;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import java.util.function.Supplier;

/** characteristic describes the current temperature of the environment in Celsius */
public class CurrentTemperatureCharacteristic extends FloatCharacteristic {

  public CurrentTemperatureCharacteristic(
      Supplier<CompletableFuture<Double>> getter,
      Consumer<HomekitCharacteristicChangeCallback> subscriber,
      Runnable unsubscriber) {
    super(
        "0000011-0000-1000-8000-0026BB765291",
        "current temperature",
        0,
        100,
        0.1,
        "C",
        Optional.of(getter),
        Optional.empty(),
        Optional.of(subscriber),
        Optional.of(unsubscriber));
  }
}
