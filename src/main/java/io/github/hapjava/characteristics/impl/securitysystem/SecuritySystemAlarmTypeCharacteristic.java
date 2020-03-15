package io.github.hapjava.characteristics.impl.securitysystem;

import io.github.hapjava.characteristics.HomekitCharacteristicChangeCallback;
import io.github.hapjava.characteristics.impl.base.EnumCharacteristic;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * This characteristic describes the type of alarm triggered by a security system. A value of 1
 * indicates an ʼunknownʼ cause. Value should revert to 0 when the alarm conditions are cleared.
 */
public class SecuritySystemAlarmTypeCharacteristic
    extends EnumCharacteristic<CurrentSecuritySystemStateEnum> {

  public SecuritySystemAlarmTypeCharacteristic(
      Supplier<CompletableFuture<CurrentSecuritySystemStateEnum>> getter,
      Consumer<HomekitCharacteristicChangeCallback> subscriber,
      Runnable unsubscriber) {
    super(
        "0000008E-0000-1000-8000-0026BB765291",
        "Alert Type",
        1,
        Optional.of(getter),
        Optional.empty(),
        Optional.of(subscriber),
        Optional.of(unsubscriber));
  }
}
