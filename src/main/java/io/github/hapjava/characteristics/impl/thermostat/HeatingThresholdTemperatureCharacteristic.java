package io.github.hapjava.characteristics.impl.thermostat;

import io.github.hapjava.characteristics.ExceptionalConsumer;
import io.github.hapjava.characteristics.HomekitCharacteristicChangeCallback;
import io.github.hapjava.characteristics.impl.base.FloatCharacteristic;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * This characteristic describes the heating threshold in Celsius for accessories that support
 * simultaneous heating and cooling. The value of this characteristic represents the minimum
 * temperature that must be reached before heating is turned on.
 */
public class HeatingThresholdTemperatureCharacteristic extends FloatCharacteristic {

  public HeatingThresholdTemperatureCharacteristic(
      Supplier<CompletableFuture<Double>> getter,
      ExceptionalConsumer<Double> setter,
      Consumer<HomekitCharacteristicChangeCallback> subscriber,
      Runnable unsubscriber) {
    super(
        "00000012-0000-1000-8000-0026BB765291",
        "heating threshold",
        0,
        25,
        0.1,
        "C",
        Optional.of(getter),
        Optional.of(setter),
        Optional.of(subscriber),
        Optional.of(unsubscriber));
  }
}
