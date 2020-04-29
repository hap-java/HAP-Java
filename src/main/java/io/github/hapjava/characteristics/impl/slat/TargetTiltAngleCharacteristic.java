package io.github.hapjava.characteristics.impl.slat;

import io.github.hapjava.characteristics.EventableCharacteristic;
import io.github.hapjava.characteristics.ExceptionalConsumer;
import io.github.hapjava.characteristics.HomekitCharacteristicChangeCallback;
import io.github.hapjava.characteristics.impl.base.IntegerCharacteristic;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * This characteristic describes the target angle of slats for accessories such as windows, fans,
 * portable heater/coolers etc.
 */
public class TargetTiltAngleCharacteristic extends IntegerCharacteristic
    implements EventableCharacteristic {

  public TargetTiltAngleCharacteristic(
      Supplier<CompletableFuture<Integer>> getter,
      ExceptionalConsumer<Integer> setter,
      Consumer<HomekitCharacteristicChangeCallback> subscriber,
      Runnable unsubscriber) {
    super(
        "000000C2-0000-1000-8000-0026BB765291",
        "target tilt angle",
        -90,
        90,
        "arcdegrees",
        Optional.of(getter),
        Optional.of(setter),
        Optional.of(subscriber),
        Optional.of(unsubscriber));
  }
}
