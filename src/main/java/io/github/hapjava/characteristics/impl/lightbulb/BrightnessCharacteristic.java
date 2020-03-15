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
 * This characteristic describes a perceived level of brightness, e.g. for lighting, and can be used
 * for backlights or color. The value is expressed as a percentage (%) of the maximum level of
 * supported brightness.
 */
public class BrightnessCharacteristic extends IntegerCharacteristic
    implements EventableCharacteristic {

  public BrightnessCharacteristic(
      Supplier<CompletableFuture<Integer>> getter,
      ExceptionalConsumer<Integer> setter,
      Consumer<HomekitCharacteristicChangeCallback> subscriber,
      Runnable unsubscriber) {
    super(
        "00000008-0000-1000-8000-0026BB765291",
        "level of brightness",
        0,
        100,
        "%",
        Optional.of(getter),
        Optional.of(setter),
        Optional.of(subscriber),
        Optional.of(unsubscriber));
  }
}
