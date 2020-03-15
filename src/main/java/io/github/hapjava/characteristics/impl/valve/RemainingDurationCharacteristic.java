package io.github.hapjava.characteristics.impl.valve;

import io.github.hapjava.characteristics.EventableCharacteristic;
import io.github.hapjava.characteristics.HomekitCharacteristicChangeCallback;
import io.github.hapjava.characteristics.impl.base.IntegerCharacteristic;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * This characteristic describes the remaining duration on the accessory. Notifications on this
 * characteristic must only be used if the remaining duration increases/decreases from the
 * accessoryʼs usual countdown of remaining duration and when the duration reaches 0. e.g. It must
 * not send notifications when the remaining duration is ticking down from 100,99,98... if 100 was
 * the initial Set duration. However, if the remaining duration changes to 95 from 92 (increase) or
 * 85 from 92 (decrease which is not part of the usual duration countdown), it must send a
 * notification. This duration is defined in seconds.
 */
public class RemainingDurationCharacteristic extends IntegerCharacteristic
    implements EventableCharacteristic {

  public RemainingDurationCharacteristic(
      Supplier<CompletableFuture<Integer>> getter,
      Consumer<HomekitCharacteristicChangeCallback> subscriber,
      Runnable unsubscriber) {
    super(
        "000000D4-0000-1000-8000-0026BB765291",
        "remaining duration",
        0,
        3600,
        "s",
        Optional.of(getter),
        Optional.empty(),
        Optional.of(subscriber),
        Optional.of(unsubscriber));
  }
}
