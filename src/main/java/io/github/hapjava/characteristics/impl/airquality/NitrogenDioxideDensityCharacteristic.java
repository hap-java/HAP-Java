package io.github.hapjava.characteristics.impl.airquality;

import io.github.hapjava.characteristics.HomekitCharacteristicChangeCallback;
import io.github.hapjava.characteristics.impl.base.FloatCharacteristic;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import java.util.function.Supplier;

/** This characteristic contains the current NO2 density in micrograms/m3. */
public class NitrogenDioxideDensityCharacteristic extends FloatCharacteristic {

  public NitrogenDioxideDensityCharacteristic(
      Supplier<CompletableFuture<Double>> getter,
      Consumer<HomekitCharacteristicChangeCallback> subscriber,
      Runnable unsubscriber) {
    super(
        "000000C4-0000-1000-8000-0026BB765291",
        "nitrogen dioxide density",
        0,
        1000,
        1,
        "micrograms",
        Optional.of(getter),
        Optional.empty(),
        Optional.of(subscriber),
        Optional.of(unsubscriber));
  }
}
