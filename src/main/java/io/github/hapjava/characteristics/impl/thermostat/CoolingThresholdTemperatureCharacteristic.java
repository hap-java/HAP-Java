package io.github.hapjava.characteristics.impl.thermostat;

import io.github.hapjava.characteristics.ExceptionalConsumer;
import io.github.hapjava.characteristics.impl.base.FloatCharacteristic;
import io.github.hapjava.characteristics.HomekitCharacteristicChangeCallback;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * This characteristic describes the cooling threshold in Celsius for accessories that support
 * simultaneous heating and cooling. The value of this characteristic represents the maximum
 * temperature that must be reached before cooling is turned on.
 */
public class CoolingThresholdTemperatureCharacteristic extends FloatCharacteristic {

  public CoolingThresholdTemperatureCharacteristic(
      Supplier<CompletableFuture<Double>> getter,
      ExceptionalConsumer<Double> setter,
      Consumer<HomekitCharacteristicChangeCallback> subscriber,
      Runnable unsubscriber) {
    super(
        "0000000D-0000-1000-8000-0026BB765291",
        "cooling threshold",
        10,
        35,
        0.1,
        "C",
        Optional.of(getter),
        Optional.of(setter),
        Optional.of(subscriber),
        Optional.of(unsubscriber));
  }
}
