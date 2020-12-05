package io.github.hapjava.characteristics.impl.leaksensor;

import io.github.hapjava.characteristics.EventableCharacteristic;
import io.github.hapjava.characteristics.HomekitCharacteristicChangeCallback;
import io.github.hapjava.characteristics.impl.base.EnumCharacteristic;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * This characteristic indicates if a sensor detected a leak (e.g. water leak, gas leak). See {@link
 * LeakDetectedStateEnum} for possible values
 */
public class LeakDetectedStateCharacteristic extends EnumCharacteristic<LeakDetectedStateEnum>
    implements EventableCharacteristic {

  public LeakDetectedStateCharacteristic(
      Supplier<CompletableFuture<LeakDetectedStateEnum>> getter,
      Consumer<HomekitCharacteristicChangeCallback> subscriber,
      Runnable unsubscriber) {
    super(
        "00000070-0000-1000-8000-0026BB765291",
        "Leak Detected State",
        LeakDetectedStateEnum.values(),
        Optional.of(getter),
        Optional.empty(),
        Optional.of(subscriber),
        Optional.of(unsubscriber));
  }
}
