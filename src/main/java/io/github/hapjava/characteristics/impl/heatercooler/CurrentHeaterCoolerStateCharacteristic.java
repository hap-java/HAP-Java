package io.github.hapjava.characteristics.impl.heatercooler;

import io.github.hapjava.characteristics.EventableCharacteristic;
import io.github.hapjava.characteristics.HomekitCharacteristicChangeCallback;
import io.github.hapjava.characteristics.impl.base.EnumCharacteristic;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import java.util.function.Supplier;

/** This characteristic describes the current state of a heater cooler. */
public class CurrentHeaterCoolerStateCharacteristic
    extends EnumCharacteristic<CurrentHeaterCoolerStateEnum> implements EventableCharacteristic {

  public CurrentHeaterCoolerStateCharacteristic(
      CurrentHeaterCoolerStateEnum[] validValues,
      Supplier<CompletableFuture<CurrentHeaterCoolerStateEnum>> getter,
      Consumer<HomekitCharacteristicChangeCallback> subscriber,
      Runnable unsubscriber) {
    super(
        "000000B1-0000-1000-8000-0026BB765291",
        "current heater cooler state",
        validValues,
        Optional.of(getter),
        Optional.empty(),
        Optional.of(subscriber),
        Optional.of(unsubscriber));
  }
}
