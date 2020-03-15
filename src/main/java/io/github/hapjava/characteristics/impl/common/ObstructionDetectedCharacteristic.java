package io.github.hapjava.characteristics.impl.common;

import io.github.hapjava.characteristics.HomekitCharacteristicChangeCallback;
import io.github.hapjava.characteristics.impl.base.BooleanCharacteristic;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * This characteristic describes the current state of an obstruction sensor, such as one that is
 * used in a garage door. If the state is true then there is an obstruction detected.
 */
public class ObstructionDetectedCharacteristic extends BooleanCharacteristic {
  public ObstructionDetectedCharacteristic(
      Supplier<CompletableFuture<Boolean>> getter,
      Consumer<HomekitCharacteristicChangeCallback> subscriber,
      Runnable unsubscriber) {
    super(
        "00000024-0000-1000-8000-0026BB765291",
        "Current state of an obstruction sensor",
        Optional.of(getter),
        Optional.empty(),
        Optional.of(subscriber),
        Optional.of(unsubscriber));
  }
}
