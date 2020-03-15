package io.github.hapjava.characteristics.impl.battery;

import io.github.hapjava.characteristics.impl.base.EnumCharacteristic;
import io.github.hapjava.characteristics.HomekitCharacteristicChangeCallback;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * This characteristic describes an accessoryʼs battery status. A status of 1 indicates that the
 * battery level of the accessory is low. Value should return to 0 when the battery charges to a
 * level thats above the low threshold.
 */
public class StatusLowBatteryCharacteristic extends EnumCharacteristic<StatusLowBatteryEnum> {

  public StatusLowBatteryCharacteristic(
      Supplier<CompletableFuture<StatusLowBatteryEnum>> getter,
      Consumer<HomekitCharacteristicChangeCallback> subscriber,
      Runnable unsubscriber) {
    super(
        "00000079-0000-1000-8000-0026BB765291",
        "Status Low Battery",
        1,
        Optional.of(getter),
        Optional.empty(),
        Optional.of(subscriber),
        Optional.of(unsubscriber));
  }
}
