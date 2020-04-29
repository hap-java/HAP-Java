package io.github.hapjava.characteristics.impl.airpurifier;

import io.github.hapjava.characteristics.ExceptionalConsumer;
import io.github.hapjava.characteristics.HomekitCharacteristicChangeCallback;
import io.github.hapjava.characteristics.impl.base.EnumCharacteristic;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import java.util.function.Supplier;

/** This characteristic describes the target state of the air purifier. */
public class TargetAirPurifierStateCharacteristic
    extends EnumCharacteristic<TargetAirPurifierStateEnum> {

  public TargetAirPurifierStateCharacteristic(
      Supplier<CompletableFuture<TargetAirPurifierStateEnum>> getter,
      ExceptionalConsumer<TargetAirPurifierStateEnum> setter,
      Consumer<HomekitCharacteristicChangeCallback> subscriber,
      Runnable unsubscriber) {
    super(
        "000000A8-0000-1000-8000-0026BB765291",
        "Air purifier state",
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
    setter.get().accept(TargetAirPurifierStateEnum.fromCode(value));
  }
}
