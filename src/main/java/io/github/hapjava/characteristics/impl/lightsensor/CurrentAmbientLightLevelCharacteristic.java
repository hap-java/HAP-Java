package io.github.hapjava.characteristics.impl.lightsensor;

import io.github.hapjava.characteristics.HomekitCharacteristicChangeCallback;
import io.github.hapjava.characteristics.impl.base.FloatCharacteristic;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import java.util.function.Supplier;

/** This characteristic indicates the current light level in Lux */
public class CurrentAmbientLightLevelCharacteristic extends FloatCharacteristic {

  public CurrentAmbientLightLevelCharacteristic(
      Supplier<CompletableFuture<Double>> getter,
      Consumer<HomekitCharacteristicChangeCallback> subscriber,
      Runnable unsubscriber) {
    super(
        "0000006B-0000-1000-8000-0026BB765291",
        "ambient light level",
        0.0001,
        100000,
        0.0001,
        "lux",
        Optional.of(getter),
        Optional.empty(),
        Optional.of(subscriber),
        Optional.of(unsubscriber));
  }
}
