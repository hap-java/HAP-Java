package io.github.hapjava.characteristics.impl.humidifier;

import io.github.hapjava.characteristics.ExceptionalConsumer;
import io.github.hapjava.characteristics.HomekitCharacteristicChangeCallback;
import io.github.hapjava.characteristics.impl.base.FloatCharacteristic;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * This characteristic describes the relative humidity dehumidifier threshold on which dehumidifier
 * turns on.
 */
public class HumidityDehumidifierThresholdCharacteristic extends FloatCharacteristic {

  public HumidityDehumidifierThresholdCharacteristic(
      Supplier<CompletableFuture<Double>> getter,
      ExceptionalConsumer<Double> setter,
      Consumer<HomekitCharacteristicChangeCallback> subscriber,
      Runnable unsubscriber) {
    super(
        "000000C9-0000-1000-8000-0026BB765291",
        "humidity threshold dehumidifier",
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
