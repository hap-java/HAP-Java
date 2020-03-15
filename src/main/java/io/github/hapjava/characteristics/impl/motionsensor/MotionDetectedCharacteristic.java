package io.github.hapjava.characteristics.impl.motionsensor;

import io.github.hapjava.characteristics.impl.base.BooleanCharacteristic;
import io.github.hapjava.characteristics.HomekitCharacteristicChangeCallback;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import java.util.function.Supplier;

/** This characteristic indicates if motion (e.g. a person moving) was detected. */
public class MotionDetectedCharacteristic extends BooleanCharacteristic {

  public MotionDetectedCharacteristic(
      Supplier<CompletableFuture<Boolean>> getter,
      Consumer<HomekitCharacteristicChangeCallback> subscriber,
      Runnable unsubscriber) {
    super(
        "00000022-0000-1000-8000-0026BB765291",
        "Motion detection",
        Optional.of(getter),
        Optional.empty(),
        Optional.of(subscriber),
        Optional.of(unsubscriber));
  }
}
