package io.github.hapjava.characteristics.impl.lock;

import io.github.hapjava.characteristics.ExceptionalConsumer;
import io.github.hapjava.characteristics.HomekitCharacteristicChangeCallback;
import io.github.hapjava.characteristics.impl.base.EnumCharacteristic;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import java.util.function.Supplier;

/** The target state of the physical security mechanism (e.g. deadbolt). */
public class LockTargetStateCharacteristic extends EnumCharacteristic<LockTargetStateEnum> {

  public LockTargetStateCharacteristic(
      Supplier<CompletableFuture<LockTargetStateEnum>> getter,
      ExceptionalConsumer<LockTargetStateEnum> setter,
      Consumer<HomekitCharacteristicChangeCallback> subscriber,
      Runnable unsubscriber) {
    super(
        "0000001E-0000-1000-8000-0026BB765291",
        "Target Lock State",
        1,
        Optional.of(getter),
        Optional.of(setter),
        Optional.of(subscriber),
        Optional.of(unsubscriber));
  }

  @Override
  protected void setValue(Integer value) throws Exception {
    if (!setter.isPresent()) {
      return;
    }
    setter.get().accept(LockTargetStateEnum.fromCode(value));
  }
}
