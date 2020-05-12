package io.github.hapjava.characteristics.impl.thermostat;

import io.github.hapjava.characteristics.HomekitCharacteristicChangeCallback;
import io.github.hapjava.characteristics.impl.base.EnumCharacteristic;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * This characteristic describes the current mode of an accessory that supports cooling or heating
 */
public class CurrentHeatingCoolingStateCharacteristic
    extends EnumCharacteristic<CurrentHeatingCoolingStateEnum> {

  public CurrentHeatingCoolingStateCharacteristic(
      Supplier<CompletableFuture<CurrentHeatingCoolingStateEnum>> getter,
      Consumer<HomekitCharacteristicChangeCallback> subscriber,
      Runnable unsubscriber) {
    super(
        "0000000F-0000-1000-8000-0026BB765291",
        "Current heating cooling mode",
        2,
        Optional.of(getter),
        Optional.empty(),
        Optional.of(subscriber),
        Optional.of(unsubscriber));
  }
}
