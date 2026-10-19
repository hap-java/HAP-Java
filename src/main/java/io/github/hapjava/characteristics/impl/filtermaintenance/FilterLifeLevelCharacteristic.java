package io.github.hapjava.characteristics.impl.filtermaintenance;

import io.github.hapjava.characteristics.EventableCharacteristic;
import io.github.hapjava.characteristics.HomekitCharacteristicChangeCallback;
import io.github.hapjava.characteristics.impl.base.FloatCharacteristic;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class FilterLifeLevelCharacteristic extends FloatCharacteristic
    implements EventableCharacteristic {

  public FilterLifeLevelCharacteristic(
      Supplier<CompletableFuture<Double>> getter,
      Consumer<HomekitCharacteristicChangeCallback> subscriber,
      Runnable unsubscriber) {
    super(
        "000000AB-0000-1000-8000-0026BB765291",
        "Filter Life Level",
        0,
        100,
        1,
        "%",
        Optional.of(getter),
        Optional.empty(),
        Optional.of(subscriber),
        Optional.of(unsubscriber));
  }
}
