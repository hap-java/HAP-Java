package io.github.hapjava.characteristics.impl.thermostat;

import io.github.hapjava.characteristics.ExceptionalConsumer;
import io.github.hapjava.characteristics.HomekitCharacteristicChangeCallback;
import io.github.hapjava.characteristics.impl.base.EnumCharacteristic;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import java.util.function.Supplier;

/** This characteristic describes the target mode of an accessory that supports heating/cooling, */
public class TargetHeatingCoolingStateCharacteristic
    extends EnumCharacteristic<TargetHeatingCoolingStateEnum> {

  public TargetHeatingCoolingStateCharacteristic(
      TargetHeatingCoolingStateEnum[] validValues,
      Supplier<CompletableFuture<TargetHeatingCoolingStateEnum>> getter,
      ExceptionalConsumer<TargetHeatingCoolingStateEnum> setter,
      Consumer<HomekitCharacteristicChangeCallback> subscriber,
      Runnable unsubscriber) {
    super(
        "00000033-0000-1000-8000-0026BB765291",
        "Target heating cooling mode",
        validValues,
        Optional.of(getter),
        Optional.of(setter),
        Optional.of(subscriber),
        Optional.of(unsubscriber));
  }
}
