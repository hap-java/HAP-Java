package io.github.hapjava.characteristics.impl.outlet;

import io.github.hapjava.characteristics.impl.base.BooleanCharacteristic;
import io.github.hapjava.characteristics.HomekitCharacteristicChangeCallback;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * This characteristic describes if the power outlet has an appliance e.g., a floor lamp, physically
 * plugged in. This characteristic is set to True even if the plugged-in appliance is off.
 */
public class OutletInUseCharacteristic extends BooleanCharacteristic {

  public OutletInUseCharacteristic(
      Supplier<CompletableFuture<Boolean>> getter,
      Consumer<HomekitCharacteristicChangeCallback> subscriber,
      Runnable unsubscriber) {
    super(
        "00000026-0000-1000-8000-0026BB765291",
        "Outlet in Use",
        Optional.of(getter),
        Optional.empty(),
        Optional.of(subscriber),
        Optional.of(unsubscriber));
  }
}
