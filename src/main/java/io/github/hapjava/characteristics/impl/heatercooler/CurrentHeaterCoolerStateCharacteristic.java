package io.github.hapjava.characteristics.impl.heatercooler;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import java.util.function.Supplier;
import io.github.hapjava.characteristics.EventableCharacteristic;
import io.github.hapjava.characteristics.ExceptionalConsumer;
import io.github.hapjava.characteristics.HomekitCharacteristicChangeCallback;
import io.github.hapjava.characteristics.impl.base.EnumCharacteristic;

/**
 * This characteristic describes the current state of a heater cooler.
 */
public class CurrentHeaterCoolerStateCharacteristic extends EnumCharacteristic<CurrentHeaterCoolerStateEnum>
    implements EventableCharacteristic {

  public CurrentHeaterCoolerStateCharacteristic(
      Supplier<CompletableFuture<CurrentHeaterCoolerStateEnum>> getter,
      Consumer<HomekitCharacteristicChangeCallback> subscriber,
      Runnable unsubscriber) {
    super(
        "000000B1-0000-1000-8000-0026BB765291",
        "current heater cooler state",
        3,
        Optional.of(getter),
        Optional.empty(),
        Optional.of(subscriber),
        Optional.of(unsubscriber));
  }
}
