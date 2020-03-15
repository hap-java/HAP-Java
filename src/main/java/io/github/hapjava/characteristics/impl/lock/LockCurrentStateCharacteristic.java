package io.github.hapjava.characteristics.impl.lock;

import io.github.hapjava.characteristics.impl.base.EnumCharacteristic;
import io.github.hapjava.characteristics.HomekitCharacteristicChangeCallback;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import java.util.function.Supplier;

/** The current state of the physical security mechanism (e.g. deadbolt). */
public class LockCurrentStateCharacteristic extends EnumCharacteristic<LockCurrentStateEnum> {

  public LockCurrentStateCharacteristic(
      Supplier<CompletableFuture<LockCurrentStateEnum>> getter,
      Consumer<HomekitCharacteristicChangeCallback> subscriber,
      Runnable unsubscriber) {
    super(
        "0000001D-0000-1000-8000-0026BB765291",
        "Current Lock State",
        3,
        Optional.of(getter),
        Optional.empty(),
        Optional.of(subscriber),
        Optional.of(unsubscriber));
  }
}
