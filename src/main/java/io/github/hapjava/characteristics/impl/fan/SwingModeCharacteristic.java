package io.github.hapjava.characteristics.impl.fan;

import io.github.hapjava.characteristics.EventableCharacteristic;
import io.github.hapjava.characteristics.ExceptionalConsumer;
import io.github.hapjava.characteristics.HomekitCharacteristicChangeCallback;
import io.github.hapjava.characteristics.impl.base.EnumCharacteristic;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import java.util.function.Supplier;

/** This characteristic describes if swing mode is enabled. */
public class SwingModeCharacteristic extends EnumCharacteristic<SwingModeEnum>
    implements EventableCharacteristic {

  public SwingModeCharacteristic(
      Supplier<CompletableFuture<SwingModeEnum>> getter,
      ExceptionalConsumer<SwingModeEnum> setter,
      Consumer<HomekitCharacteristicChangeCallback> subscriber,
      Runnable unsubscriber) {
    super(
        "000000B6-0000-1000-8000-0026BB765291",
        "Swing Mode",
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
    setter.get().accept(SwingModeEnum.fromCode(value));
  }
}
