package io.github.hapjava.characteristics.impl.humidifier;

import io.github.hapjava.characteristics.EventableCharacteristic;
import io.github.hapjava.characteristics.ExceptionalConsumer;
import io.github.hapjava.characteristics.HomekitCharacteristicChangeCallback;
import io.github.hapjava.characteristics.impl.base.EnumCharacteristic;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import java.util.function.Supplier;

/** This characteristic describes the target state of a humidifier or/and a dehumidifier.. */
public class TargetHumidifierDehumidifierStateCharacteristic
    extends EnumCharacteristic<TargetHumidifierDehumidifierStateEnum>
    implements EventableCharacteristic {

  public TargetHumidifierDehumidifierStateCharacteristic(
      Supplier<CompletableFuture<TargetHumidifierDehumidifierStateEnum>> getter,
      ExceptionalConsumer<TargetHumidifierDehumidifierStateEnum> setter,
      Consumer<HomekitCharacteristicChangeCallback> subscriber,
      Runnable unsubscriber) {
    super(
        "000000B4-0000-1000-8000-0026BB765291",
        "target humidifier state",
        TargetHumidifierDehumidifierStateEnum.values(),
        Optional.of(getter),
        Optional.of(setter),
        Optional.of(subscriber),
        Optional.of(unsubscriber));
  }
}
