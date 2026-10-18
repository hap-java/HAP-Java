package io.github.hapjava.characteristics.impl.thermostat;

import io.github.hapjava.characteristics.ExceptionalConsumer;
import io.github.hapjava.characteristics.HomekitCharacteristicChangeCallback;
import io.github.hapjava.characteristics.impl.base.EnumCharacteristic;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * This characteristic describes units of temperature used for presentation purposes (e.g. the units
 * of temperature displayed on the screen).
 */
public class TemperatureDisplayUnitCharacteristic
    extends EnumCharacteristic<TemperatureDisplayUnitEnum> {

  public TemperatureDisplayUnitCharacteristic(
      Supplier<CompletableFuture<TemperatureDisplayUnitEnum>> getter,
      ExceptionalConsumer<TemperatureDisplayUnitEnum> setter,
      Consumer<HomekitCharacteristicChangeCallback> subscriber,
      Runnable unsubscriber) {
    this(TemperatureDisplayUnitEnum.values(), getter, setter, subscriber, unsubscriber);
  }

  public TemperatureDisplayUnitCharacteristic(
      TemperatureDisplayUnitEnum[] validValues,
      Supplier<CompletableFuture<TemperatureDisplayUnitEnum>> getter,
      ExceptionalConsumer<TemperatureDisplayUnitEnum> setter,
      Consumer<HomekitCharacteristicChangeCallback> subscriber,
      Runnable unsubscriber) {
    super(
        "00000036-0000-1000-8000-0026BB765291",
        "Temperature units",
        validValues,
        Optional.of(getter),
        Optional.of(setter),
        Optional.of(subscriber),
        Optional.of(unsubscriber));
  }
}
