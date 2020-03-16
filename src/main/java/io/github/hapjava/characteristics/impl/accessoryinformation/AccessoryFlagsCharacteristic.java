package io.github.hapjava.characteristics.impl.accessoryinformation;

import io.github.hapjava.characteristics.HomekitCharacteristicChangeCallback;
import io.github.hapjava.characteristics.impl.base.EnumCharacteristic;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import java.util.function.Supplier;

/** When set indicates accessory requires additional setup. */
public class AccessoryFlagsCharacteristic extends EnumCharacteristic<AccessoryFlagsEnum> {

  public AccessoryFlagsCharacteristic(
      Supplier<CompletableFuture<AccessoryFlagsEnum>> getter,
      Consumer<HomekitCharacteristicChangeCallback> subscriber,
      Runnable unsubscriber) {
    super(
        "000000A6-0000-1000-8000-0026BB765291",
        "accessory flags",
        1,
        Optional.of(getter),
        Optional.empty(),
        Optional.of(subscriber),
        Optional.of(unsubscriber));
  }
}
