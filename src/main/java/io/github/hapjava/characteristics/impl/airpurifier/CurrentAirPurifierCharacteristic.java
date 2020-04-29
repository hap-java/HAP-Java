package io.github.hapjava.characteristics.impl.airpurifier;

import io.github.hapjava.characteristics.HomekitCharacteristicChangeCallback;
import io.github.hapjava.characteristics.impl.base.EnumCharacteristic;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import java.util.function.Supplier;

/** This characteristic describes the current state of the air purifier. */
public class CurrentAirPurifierCharacteristic
    extends EnumCharacteristic<CurrentAirPurifierStateEnum> {

  public CurrentAirPurifierCharacteristic(
      Supplier<CompletableFuture<CurrentAirPurifierStateEnum>> getter,
      Consumer<HomekitCharacteristicChangeCallback> subscriber,
      Runnable unsubscriber) {
    super(
        "000000A9-0000-1000-8000-0026BB765291",
        "current air purifier state",
        2,
        Optional.of(getter),
        Optional.empty(),
        Optional.of(subscriber),
        Optional.of(unsubscriber));
  }
}
