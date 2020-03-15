package io.github.hapjava.characteristics.impl.windowcovering;

import io.github.hapjava.characteristics.EventableCharacteristic;
import io.github.hapjava.characteristics.HomekitCharacteristicChangeCallback;
import io.github.hapjava.characteristics.impl.base.IntegerCharacteristic;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * This characteristic describes the current angle of vertical slats for accessories such as
 * windows, fans, portable heater/coolers etc. This characteristic takes values between -90 and 90.
 * A value of 0 indicates that the slats are rotated to be fully open. A value of -90 indicates that
 * the slats are rotated all the way in a direction where the user-facing edge is to the left of the
 * window-facing edge.
 */
public class CurrentVerticalTiltAngleCharacteristic extends IntegerCharacteristic
    implements EventableCharacteristic {

  public CurrentVerticalTiltAngleCharacteristic(
      Supplier<CompletableFuture<Integer>> getter,
      Consumer<HomekitCharacteristicChangeCallback> subscriber,
      Runnable unsubscriber) {
    super(
        "0000006E-0000-1000-8000-0026BB765291",
        "current vertical tilt angel",
        -90,
        90,
        "arcdegrees",
        Optional.of(getter),
        Optional.empty(),
        Optional.of(subscriber),
        Optional.of(unsubscriber));
  }
}
