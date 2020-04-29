package io.github.hapjava.characteristics.impl.audio;

import io.github.hapjava.characteristics.EventableCharacteristic;
import io.github.hapjava.characteristics.ExceptionalConsumer;
import io.github.hapjava.characteristics.HomekitCharacteristicChangeCallback;
import io.github.hapjava.characteristics.impl.base.IntegerCharacteristic;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * A Volume characteristic allows the control of input or output volume of an audio input or output
 * accessory respectively. The value of this characteristic indicates the percentage of the maximum
 * volume supported by the service.
 *
 * @author Eugen Freiter
 */
public class VolumeCharacteristic extends IntegerCharacteristic implements EventableCharacteristic {

  public VolumeCharacteristic(
      Supplier<CompletableFuture<Integer>> getter,
      ExceptionalConsumer<Integer> setter,
      Consumer<HomekitCharacteristicChangeCallback> subscriber,
      Runnable unsubscriber) {
    super(
        "00000119-0000-1000-8000-0026BB765291",
        "volume",
        0,
        100,
        "percentage",
        Optional.of(getter),
        Optional.of(setter),
        Optional.of(subscriber),
        Optional.of(unsubscriber));
  }
}
