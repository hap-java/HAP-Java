package io.github.hapjava.characteristics.impl.battery;

import io.github.hapjava.characteristics.HomekitCharacteristicChangeCallback;
import io.github.hapjava.characteristics.impl.base.EnumCharacteristic;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * This characteristic describes an accessory battery status. See {@link StatusLowBatteryEnum} for
 * possible values
 */
public class StatusLowBatteryCharacteristic extends EnumCharacteristic<StatusLowBatteryEnum> {

  public StatusLowBatteryCharacteristic(
      Supplier<CompletableFuture<StatusLowBatteryEnum>> getter,
      Consumer<HomekitCharacteristicChangeCallback> subscriber,
      Runnable unsubscriber) {
    super(
        "00000079-0000-1000-8000-0026BB765291",
        "Status Low Battery",
        StatusLowBatteryEnum.values(),
        Optional.of(getter),
        Optional.empty(),
        Optional.of(subscriber),
        Optional.of(unsubscriber));
  }
}
