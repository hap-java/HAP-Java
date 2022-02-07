package io.github.hapjava.characteristics.impl.television;

import io.github.hapjava.characteristics.ExceptionalConsumer;
import io.github.hapjava.characteristics.HomekitCharacteristicChangeCallback;
import io.github.hapjava.characteristics.impl.base.EnumCharacteristic;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * This characteristic retrieves / sets picture mode. See {@link PictureModeEnum} for possible
 * values.
 */
public class PictureModeCharacteristic extends EnumCharacteristic<PictureModeEnum> {
  public PictureModeCharacteristic(
      Supplier<CompletableFuture<PictureModeEnum>> getter,
      ExceptionalConsumer<PictureModeEnum> setter,
      Consumer<HomekitCharacteristicChangeCallback> subscriber,
      Runnable unsubscriber) {
    super(
        "000000E2-0000-1000-8000-0026BB765291",
        "picture mode",
        PictureModeEnum.values(),
        Optional.of(getter),
        Optional.of(setter),
        Optional.of(subscriber),
        Optional.of(unsubscriber));
  }
}
