package io.github.hapjava.characteristics.impl.thermostat;

import io.github.hapjava.characteristics.HomekitCharacteristicChangeCallback;
import io.github.hapjava.characteristics.impl.base.FloatCharacteristic;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import java.util.function.Supplier;

/** characteristic describes the current temperature of the environment in Celsius */
public class CurrentTemperatureCharacteristic extends FloatCharacteristic {

  public CurrentTemperatureCharacteristic(
      double minValue,
      double maxValue,
      double minStep,
      Supplier<CompletableFuture<Double>> getter,
      Consumer<HomekitCharacteristicChangeCallback> subscriber,
      Runnable unsubscriber) {
    super(
        "00000011-0000-1000-8000-0026BB765291",
        "current temperature",
        minValue,
        maxValue,
        minStep,
        "C",
        Optional.of(getter),
        Optional.empty(),
        Optional.of(subscriber),
        Optional.of(unsubscriber));
  }

  public CurrentTemperatureCharacteristic(
      Supplier<CompletableFuture<Double>> getter,
      Consumer<HomekitCharacteristicChangeCallback> subscriber,
      Runnable unsubscriber) {
    this(0, 100, 0.1, getter, subscriber, unsubscriber);
  }
}
