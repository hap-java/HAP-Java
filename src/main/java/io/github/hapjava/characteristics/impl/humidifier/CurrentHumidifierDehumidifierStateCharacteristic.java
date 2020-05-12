package io.github.hapjava.characteristics.impl.humidifier;

import io.github.hapjava.characteristics.EventableCharacteristic;
import io.github.hapjava.characteristics.HomekitCharacteristicChangeCallback;
import io.github.hapjava.characteristics.impl.base.EnumCharacteristic;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import java.util.function.Supplier;

/** This characteristic describes the current state of a humidifier or/and a dehumidifier. */
public class CurrentHumidifierDehumidifierStateCharacteristic
    extends EnumCharacteristic<CurrentHumidifierDehumidifierStateEnum>
    implements EventableCharacteristic {

  public CurrentHumidifierDehumidifierStateCharacteristic(
      Supplier<CompletableFuture<CurrentHumidifierDehumidifierStateEnum>> getter,
      Consumer<HomekitCharacteristicChangeCallback> subscriber,
      Runnable unsubscriber) {
    super(
        "000000B3-0000-1000-8000-0026BB765291",
        "current humidifier state",
        3,
        Optional.of(getter),
        Optional.empty(),
        Optional.of(subscriber),
        Optional.of(unsubscriber));
  }
}
