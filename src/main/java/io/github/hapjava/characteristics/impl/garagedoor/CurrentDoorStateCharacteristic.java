package io.github.hapjava.characteristics.impl.garagedoor;

import io.github.hapjava.characteristics.EventableCharacteristic;
import io.github.hapjava.characteristics.HomekitCharacteristicChangeCallback;
import io.github.hapjava.characteristics.impl.base.EnumCharacteristic;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import java.util.function.Supplier;

/** This characteristic describes the current state of a door. */
public class CurrentDoorStateCharacteristic extends EnumCharacteristic<CurrentDoorStateEnum>
    implements EventableCharacteristic {

  public CurrentDoorStateCharacteristic(
      Supplier<CompletableFuture<CurrentDoorStateEnum>> getter,
      Consumer<HomekitCharacteristicChangeCallback> subscriber,
      Runnable unsubscriber) {
    super(
        "0000000E-0000-1000-8000-0026BB765291",
        "Current Door State",
        4,
        Optional.of(getter),
        Optional.empty(),
        Optional.of(subscriber),
        Optional.of(unsubscriber));
  }
}
