package io.github.hapjava.characteristics.impl.battery;

import io.github.hapjava.characteristics.HomekitCharacteristicChangeCallback;
import io.github.hapjava.characteristics.impl.base.EnumCharacteristic;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import java.util.function.Supplier;

/** This characteristic describes the charging state of a battery or an accessory. */
public class ChargingStateCharacteristic extends EnumCharacteristic<ChargingStateEnum> {

  public ChargingStateCharacteristic(
      Supplier<CompletableFuture<ChargingStateEnum>> getter,
      Consumer<HomekitCharacteristicChangeCallback> subscriber,
      Runnable unsubscriber) {
    super(
        "0000008F-0000-1000-8000-0026BB765291",
        "Charging state",
        2,
        Optional.of(getter),
        Optional.empty(),
        Optional.of(subscriber),
        Optional.of(unsubscriber));
  }
}
