package io.github.hapjava.characteristics.impl.lightbulb;

import io.github.hapjava.characteristics.ExceptionalConsumer;
import io.github.hapjava.characteristics.impl.base.FloatCharacteristic;
import io.github.hapjava.characteristics.HomekitCharacteristicChangeCallback;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import java.util.function.Supplier;

/** This characteristic describes hue or color. */
public class HueCharacteristic extends FloatCharacteristic {
  public HueCharacteristic(
      Supplier<CompletableFuture<Double>> getter,
      ExceptionalConsumer<Double> setter,
      Consumer<HomekitCharacteristicChangeCallback> subscriber,
      Runnable unsubscriber) {
    super(
        "00000013-0000-1000-8000-0026BB765291",
        "hue or color",
        0,
        360,
        1,
        "arcdegrees",
        Optional.of(getter),
        Optional.of(setter),
        Optional.of(subscriber),
        Optional.of(unsubscriber));
  }
}
