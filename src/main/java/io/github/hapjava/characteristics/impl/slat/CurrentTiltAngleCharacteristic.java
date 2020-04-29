package io.github.hapjava.characteristics.impl.slat;

import io.github.hapjava.characteristics.EventableCharacteristic;
import io.github.hapjava.characteristics.HomekitCharacteristicChangeCallback;
import io.github.hapjava.characteristics.impl.base.IntegerCharacteristic;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * etc. This characteristic takes values between -90 and 90. A value of 0 indicates that the slats
 * are rotated to be fully open. At value 0 the user-facing edge and the window-facing edge are
 * perpendicular to the window.
 */
public class CurrentTiltAngleCharacteristic extends IntegerCharacteristic
    implements EventableCharacteristic {

  public CurrentTiltAngleCharacteristic(
      Supplier<CompletableFuture<Integer>> getter,
      Consumer<HomekitCharacteristicChangeCallback> subscriber,
      Runnable unsubscriber) {
    super(
        "000000C1-0000-1000-8000-0026BB765291",
        "current tilt angle",
        -90,
        90,
        "arcdegrees",
        Optional.of(getter),
        Optional.empty(),
        Optional.of(subscriber),
        Optional.of(unsubscriber));
  }
}
