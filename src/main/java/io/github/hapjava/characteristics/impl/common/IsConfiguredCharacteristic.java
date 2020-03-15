package io.github.hapjava.characteristics.impl.common;

import io.github.hapjava.characteristics.impl.base.EnumCharacteristic;
import io.github.hapjava.characteristics.ExceptionalConsumer;
import io.github.hapjava.characteristics.HomekitCharacteristicChangeCallback;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * This characteristic describes if the service is configured for use. For example, all of the
 * valves in an irrigation system may not be configured depending on physical wire connection. If
 * the accessory supports updating through HAP, then it must also advertise Paired Write in the
 * permissions.
 */
public class IsConfiguredCharacteristic extends EnumCharacteristic<IsConfiguredEnum> {
  public IsConfiguredCharacteristic(
      Supplier<CompletableFuture<IsConfiguredEnum>> getter,
      ExceptionalConsumer<IsConfiguredEnum> setter,
      Consumer<HomekitCharacteristicChangeCallback> subscriber,
      Runnable unsubscriber) {
    super(
        "000000D6-0000-1000-8000-0026BB765291",
        "Is Configured",
        1,
        Optional.of(getter),
        Optional.of(setter),
        Optional.of(subscriber),
        Optional.of(unsubscriber));
  }

  @Override
  protected void setValue(Integer value) throws Exception {
    if (setter.isPresent()) setter.get().accept(IsConfiguredEnum.fromCode(value));
  }
}
