package io.github.hapjava.characteristics.impl.occupancysensor;

import io.github.hapjava.characteristics.impl.base.EnumCharacteristic;
import io.github.hapjava.characteristics.HomekitCharacteristicChangeCallback;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import java.util.function.Supplier;

/** This characteristic indicates if occupancy was detected (e.g. a person present). */
public class OccupancyDetectedCharacteristic extends EnumCharacteristic<OccupancyDetectedEnum> {

  public OccupancyDetectedCharacteristic(
      Supplier<CompletableFuture<OccupancyDetectedEnum>> getter,
      Consumer<HomekitCharacteristicChangeCallback> subscriber,
      Runnable unsubscriber) {
    super(
        "00000071-0000-1000-8000-0026BB765291",
        "Occupancy Detected",
        1,
        Optional.of(getter),
        Optional.empty(),
        Optional.of(subscriber),
        Optional.of(unsubscriber));
  }
}
