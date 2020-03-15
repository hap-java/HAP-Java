package io.github.hapjava.characteristics.impl.carbonmonoxidesensor;

import io.github.hapjava.characteristics.impl.base.FloatCharacteristic;
import io.github.hapjava.characteristics.HomekitCharacteristicChangeCallback;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * This characteristic indicates the highest detected level (ppm) of Carbon Monoxide detected by a
 * sensor.
 */
public class CarbonMonoxidePeakLevelCharacteristic extends FloatCharacteristic {

  public CarbonMonoxidePeakLevelCharacteristic(
      Supplier<CompletableFuture<Double>> getter,
      Consumer<HomekitCharacteristicChangeCallback> subscriber,
      Runnable unsubscriber) {
    super(
        "00000091-0000-1000-8000-0026BB765291",
        "Carbon Monoxide Peak Level",
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
