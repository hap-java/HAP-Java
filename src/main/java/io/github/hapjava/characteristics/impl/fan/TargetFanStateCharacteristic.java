package io.github.hapjava.characteristics.impl.fan;

import io.github.hapjava.characteristics.EventableCharacteristic;
import io.github.hapjava.characteristics.ExceptionalConsumer;
import io.github.hapjava.characteristics.HomekitCharacteristicChangeCallback;
import io.github.hapjava.characteristics.impl.base.EnumCharacteristic;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import java.util.function.Supplier;

/** This characteristic describes the target state of the fan. */
public class TargetFanStateCharacteristic extends EnumCharacteristic<TargetFanStateEnum>
    implements EventableCharacteristic {

  public TargetFanStateCharacteristic(
      Supplier<CompletableFuture<TargetFanStateEnum>> getter,
      ExceptionalConsumer<TargetFanStateEnum> setter,
      Consumer<HomekitCharacteristicChangeCallback> subscriber,
      Runnable unsubscriber) {
    this(TargetFanStateEnum.values(), getter, setter, subscriber, unsubscriber);
  }

  public TargetFanStateCharacteristic(
      TargetFanStateEnum[] validValues,
      Supplier<CompletableFuture<TargetFanStateEnum>> getter,
      ExceptionalConsumer<TargetFanStateEnum> setter,
      Consumer<HomekitCharacteristicChangeCallback> subscriber,
      Runnable unsubscriber) {
    super(
        "000000BF-0000-1000-8000-0026BB765291",
        "Target Fan State",
        validValues,
        Optional.of(getter),
        Optional.of(setter),
        Optional.of(subscriber),
        Optional.of(unsubscriber));
  }
}
