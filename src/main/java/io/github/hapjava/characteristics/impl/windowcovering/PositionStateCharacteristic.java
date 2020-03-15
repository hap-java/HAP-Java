package io.github.hapjava.characteristics.impl.windowcovering;

import io.github.hapjava.characteristics.HomekitCharacteristicChangeCallback;
import io.github.hapjava.characteristics.impl.base.EnumCharacteristic;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * This characteristic describes the state of the position of accessories. This characteristic can
 * be used with doors, windows, awnings or window coverings for presentation purposes.
 */
public class PositionStateCharacteristic extends EnumCharacteristic<PositionStateEnum> {

  public PositionStateCharacteristic(
      Supplier<CompletableFuture<PositionStateEnum>> getter,
      Consumer<HomekitCharacteristicChangeCallback> subscriber,
      Runnable unsubscriber) {
    super(
        "00000072-0000-1000-8000-0026BB765291",
        "Position state",
        2,
        Optional.of(getter),
        Optional.empty(),
        Optional.of(subscriber),
        Optional.of(unsubscriber));
  }
}
