package io.github.hapjava.characteristics.impl.securitysystem;

import io.github.hapjava.characteristics.HomekitCharacteristicChangeCallback;
import io.github.hapjava.characteristics.impl.base.EnumCharacteristic;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * This characteristic describes the type of alarm triggered by a security system. See {@link
 * SecuritySystemAlarmTypeEnum} for possible values.
 */
public class SecuritySystemAlarmTypeCharacteristic
    extends EnumCharacteristic<SecuritySystemAlarmTypeEnum> {

  public SecuritySystemAlarmTypeCharacteristic(
      Supplier<CompletableFuture<SecuritySystemAlarmTypeEnum>> getter,
      Consumer<HomekitCharacteristicChangeCallback> subscriber,
      Runnable unsubscriber) {
    super(
        "0000008E-0000-1000-8000-0026BB765291",
        "Alert Type",
        SecuritySystemAlarmTypeEnum.values(),
        Optional.of(getter),
        Optional.empty(),
        Optional.of(subscriber),
        Optional.of(unsubscriber));
  }
}
