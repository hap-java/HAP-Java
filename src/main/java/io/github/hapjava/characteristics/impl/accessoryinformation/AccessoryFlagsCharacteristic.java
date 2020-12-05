package io.github.hapjava.characteristics.impl.accessoryinformation;

import io.github.hapjava.characteristics.HomekitCharacteristicChangeCallback;
import io.github.hapjava.characteristics.impl.base.EnumCharacteristic;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * This characteristic indicates whether accessory requires additional setup. See {@link
 * AccessoryFlagsEnum} for possible values.
 */
public class AccessoryFlagsCharacteristic extends EnumCharacteristic<AccessoryFlagsEnum> {

  public AccessoryFlagsCharacteristic(
      Supplier<CompletableFuture<AccessoryFlagsEnum>> getter,
      Consumer<HomekitCharacteristicChangeCallback> subscriber,
      Runnable unsubscriber) {
    super(
        "000000A6-0000-1000-8000-0026BB765291",
        "accessory flags",
        AccessoryFlagsEnum.values(),
        Optional.of(getter),
        Optional.empty(),
        Optional.of(subscriber),
        Optional.of(unsubscriber));
  }
}
