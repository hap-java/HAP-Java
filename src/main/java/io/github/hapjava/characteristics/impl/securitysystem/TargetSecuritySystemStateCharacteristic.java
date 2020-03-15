package io.github.hapjava.characteristics.impl.securitysystem;

import io.github.hapjava.characteristics.ExceptionalConsumer;
import io.github.hapjava.characteristics.HomekitCharacteristicChangeCallback;
import io.github.hapjava.characteristics.impl.base.EnumCharacteristic;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import java.util.function.Supplier;

/** This characteristic describes the target state of the security system. */
public class TargetSecuritySystemStateCharacteristic
    extends EnumCharacteristic<TargetSecuritySystemStateEnum> {

  public TargetSecuritySystemStateCharacteristic(
      Supplier<CompletableFuture<TargetSecuritySystemStateEnum>> getter,
      ExceptionalConsumer<TargetSecuritySystemStateEnum> setter,
      Consumer<HomekitCharacteristicChangeCallback> subscriber,
      Runnable unsubscriber) {
    super(
        "00000067-0000-1000-8000-0026BB765291",
        "Target Security System State",
        3,
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
    setter.get().accept(TargetSecuritySystemStateEnum.fromCode(value));
  }
}
