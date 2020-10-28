package io.github.hapjava.characteristics.impl.securitysystem;

import io.github.hapjava.characteristics.HomekitCharacteristicChangeCallback;
import io.github.hapjava.characteristics.impl.base.EnumCharacteristic;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import java.util.function.Supplier;

/** This characteristic describes the state of a security system */
public class CurrentSecuritySystemStateCharacteristic
    extends EnumCharacteristic<CurrentSecuritySystemStateEnum> {

  public CurrentSecuritySystemStateCharacteristic(
      CurrentSecuritySystemStateEnum[] validValues,
      Supplier<CompletableFuture<CurrentSecuritySystemStateEnum>> getter,
      Consumer<HomekitCharacteristicChangeCallback> subscriber,
      Runnable unsubscriber) {
    super(
        "00000066-0000-1000-8000-0026BB765291",
        "Current Security System State",
        validValues,
        Optional.of(getter),
        Optional.empty(),
        Optional.of(subscriber),
        Optional.of(unsubscriber));
  }
}
