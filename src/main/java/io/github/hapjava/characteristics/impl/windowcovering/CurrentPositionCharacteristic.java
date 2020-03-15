package io.github.hapjava.characteristics.impl.windowcovering;

import io.github.hapjava.characteristics.EventableCharacteristic;
import io.github.hapjava.characteristics.HomekitCharacteristicChangeCallback;
import io.github.hapjava.characteristics.impl.base.IntegerCharacteristic;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * This characteristic describes the current position of accessories. This characteristic can be
 * used with doors, windows, awnings or window coverings. For windows and doors, a value of 0
 * indicates that a window (or door) is fully closed while a value of 100 indicates a fully open
 * position. For blinds/shades/awnings, a value of 0 indicates a position that permits the least
 * light and a value of 100 indicates a position that allows most light.
 */
public class CurrentPositionCharacteristic extends IntegerCharacteristic
    implements EventableCharacteristic {

  public CurrentPositionCharacteristic(
      Supplier<CompletableFuture<Integer>> getter,
      Consumer<HomekitCharacteristicChangeCallback> subscriber,
      Runnable unsubscriber) {
    super(
        "0000006D-0000-1000-8000-0026BB765291",
        "current position",
        0,
        100,
        "%",
        Optional.of(getter),
        Optional.empty(),
        Optional.of(subscriber),
        Optional.of(unsubscriber));
  }
}
