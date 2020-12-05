package io.github.hapjava.characteristics.impl.garagedoor;

import io.github.hapjava.characteristics.EventableCharacteristic;
import io.github.hapjava.characteristics.ExceptionalConsumer;
import io.github.hapjava.characteristics.HomekitCharacteristicChangeCallback;
import io.github.hapjava.characteristics.impl.base.EnumCharacteristic;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import java.util.function.Supplier;

/** This characteristic describes the target state of a door. */
public class TargetDoorStateCharacteristic extends EnumCharacteristic<TargetDoorStateEnum>
    implements EventableCharacteristic {

  public TargetDoorStateCharacteristic(
      Supplier<CompletableFuture<TargetDoorStateEnum>> getter,
      ExceptionalConsumer<TargetDoorStateEnum> setter,
      Consumer<HomekitCharacteristicChangeCallback> subscriber,
      Runnable unsubscriber) {
    super(
        "00000032-0000-1000-8000-0026BB765291",
        "Target Door State",
        TargetDoorStateEnum.values(),
        Optional.of(getter),
        Optional.of(setter),
        Optional.of(subscriber),
        Optional.of(unsubscriber));
  }
}
