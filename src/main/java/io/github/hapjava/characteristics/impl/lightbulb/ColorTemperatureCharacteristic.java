package io.github.hapjava.characteristics.impl.lightbulb;

import io.github.hapjava.characteristics.EventableCharacteristic;
import io.github.hapjava.characteristics.ExceptionalConsumer;
import io.github.hapjava.characteristics.HomekitCharacteristicChangeCallback;
import io.github.hapjava.characteristics.impl.base.IntegerCharacteristic;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * This characteristic describes color temperature which is represented in reciprocal megaKelvin
 * (MK-1) or mirek scale. (M = 1,000,000 / K where M is the desired mirek value and K is temperature
 * in Kelvin)
 */
public class ColorTemperatureCharacteristic extends IntegerCharacteristic
    implements EventableCharacteristic {

  public ColorTemperatureCharacteristic(
      Supplier<CompletableFuture<Integer>> getter,
      ExceptionalConsumer<Integer> setter,
      Consumer<HomekitCharacteristicChangeCallback> subscriber,
      Runnable unsubscriber) {
    super(
        "000000CE-0000-1000-8000-0026BB765291",
        "color temperature",
        50,
        400,
        "K",
        Optional.of(getter),
        Optional.of(setter),
        Optional.of(subscriber),
        Optional.of(unsubscriber));
  }
}
