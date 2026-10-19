package io.github.hapjava.characteristics.impl.humidifier;

import io.github.hapjava.characteristics.ExceptionalConsumer;
import io.github.hapjava.characteristics.HomekitCharacteristicChangeCallback;
import io.github.hapjava.characteristics.impl.base.FloatCharacteristic;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * This characteristic describes the relative humidity humidifier threshold on which humidifier
 * turns on.
 */
public class HumidityHumidifierThresholdCharacteristic extends FloatCharacteristic {

  public HumidityHumidifierThresholdCharacteristic(
      Supplier<CompletableFuture<Double>> getter,
      ExceptionalConsumer<Double> setter,
      Consumer<HomekitCharacteristicChangeCallback> subscriber,
      Runnable unsubscriber) {
    super(
        "000000CA-0000-1000-8000-0026BB765291",
        "humidity threshold humidifier",
        0,
        100,
        1,
        "%",
        Optional.of(getter),
        Optional.of(setter),
        Optional.of(subscriber),
        Optional.of(unsubscriber));
  }
}
