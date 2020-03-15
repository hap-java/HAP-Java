package io.github.hapjava.characteristics.impl.thermostat;

import io.github.hapjava.characteristics.ExceptionalConsumer;
import io.github.hapjava.characteristics.HomekitCharacteristicChangeCallback;
import io.github.hapjava.characteristics.impl.base.FloatCharacteristic;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * This characteristic describes the target temperature in Celsius that the accessory is actively
 * attempting to reach. For example, a thermostat cooling a room to 75 degrees Fahrenheit would set
 * the target temperature value to 23.9 degrees Celsius.
 */
public class TargetTemperatureCharacteristic extends FloatCharacteristic {

  public TargetTemperatureCharacteristic(
      Supplier<CompletableFuture<Double>> getter,
      ExceptionalConsumer<Double> setter,
      Consumer<HomekitCharacteristicChangeCallback> subscriber,
      Runnable unsubscriber) {
    super(
        "00000035-0000-1000-8000-0026BB765291",
        "target temperature",
        10,
        38,
        0.1,
        "C",
        Optional.of(getter),
        Optional.of(setter),
        Optional.of(subscriber),
        Optional.of(unsubscriber));
  }
}
