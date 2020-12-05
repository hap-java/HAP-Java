package io.github.hapjava.characteristics.impl.airpurifier;

import io.github.hapjava.characteristics.ExceptionalConsumer;
import io.github.hapjava.characteristics.HomekitCharacteristicChangeCallback;
import io.github.hapjava.characteristics.impl.base.EnumCharacteristic;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * This characteristic describes the target state of the air purifier. See {@link
 * TargetAirPurifierStateEnum} for possible values.
 */
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
        TargetAirPurifierStateEnum.values(),
        Optional.of(getter),
        Optional.of(setter),
        Optional.of(subscriber),
        Optional.of(unsubscriber));
  }
}
