package io.github.hapjava.characteristics.impl.heatercooler;

import io.github.hapjava.characteristics.EventableCharacteristic;
import io.github.hapjava.characteristics.ExceptionalConsumer;
import io.github.hapjava.characteristics.HomekitCharacteristicChangeCallback;
import io.github.hapjava.characteristics.impl.base.EnumCharacteristic;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import java.util.function.Supplier;

/** This characteristic describes the target state of heater cooler. */
public class TargetHeaterCoolerStateCharacteristic
    extends EnumCharacteristic<TargetHeaterCoolerStateEnum> implements EventableCharacteristic {

  public TargetHeaterCoolerStateCharacteristic(
      TargetHeaterCoolerStateEnum[] validValues,
      Supplier<CompletableFuture<TargetHeaterCoolerStateEnum>> getter,
      ExceptionalConsumer<TargetHeaterCoolerStateEnum> setter,
      Consumer<HomekitCharacteristicChangeCallback> subscriber,
      Runnable unsubscriber) {
    super(
        "000000B2-0000-1000-8000-0026BB765291",
        "target heater cooler state",
        validValues,
        Optional.of(getter),
        Optional.of(setter),
        Optional.of(subscriber),
        Optional.of(unsubscriber));
  }
}
