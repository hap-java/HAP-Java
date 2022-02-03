package io.github.hapjava.characteristics.impl.television;

import io.github.hapjava.characteristics.ExceptionalConsumer;
import io.github.hapjava.characteristics.HomekitCharacteristicChangeCallback;
import io.github.hapjava.characteristics.impl.base.EnumCharacteristic;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import java.util.function.Supplier;

/** This characteristic sets media state. See {@link TargetMediaStateEnum} for possible values. */
public class TargetMediaStateCharacteristic extends EnumCharacteristic<TargetMediaStateEnum> {
  public TargetMediaStateCharacteristic(
      Supplier<CompletableFuture<TargetMediaStateEnum>> getter,
      ExceptionalConsumer<TargetMediaStateEnum> setter,
      Consumer<HomekitCharacteristicChangeCallback> subscriber,
      Runnable unsubscriber) {
    super(
        "00000137-0000-1000-8000-0026BB765291",
        "current media state",
        TargetMediaStateEnum.values(),
        Optional.of(getter),
        Optional.of(setter),
        Optional.of(subscriber),
        Optional.of(unsubscriber));
  }
}
