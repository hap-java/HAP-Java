package io.github.hapjava.characteristics.impl.fan;

import io.github.hapjava.characteristics.impl.base.EnumCharacteristic;
import io.github.hapjava.characteristics.EventableCharacteristic;
import io.github.hapjava.characteristics.ExceptionalConsumer;
import io.github.hapjava.characteristics.HomekitCharacteristicChangeCallback;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * This characteristic describes a way to lock a set of physical controls on an accessory (eg. child
 * lock).
 */
public class LockPhysicalControlsCharacteristic extends EnumCharacteristic<LockPhysicalControlsEnum>
    implements EventableCharacteristic {

  public LockPhysicalControlsCharacteristic(
      Supplier<CompletableFuture<LockPhysicalControlsEnum>> getter,
      ExceptionalConsumer<LockPhysicalControlsEnum> setter,
      Consumer<HomekitCharacteristicChangeCallback> subscriber,
      Runnable unsubscriber) {
    super(
        "000000A7-0000-1000-8000-0026BB765291",
        "Lock Physical Locks",
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
    setter.get().accept(LockPhysicalControlsEnum.fromCode(value));
  }
}
