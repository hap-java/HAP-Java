package io.github.hapjava.characteristics.impl.carbonmonoxidesensor;

import io.github.hapjava.characteristics.impl.base.EnumCharacteristic;
import io.github.hapjava.characteristics.EventableCharacteristic;
import io.github.hapjava.characteristics.HomekitCharacteristicChangeCallback;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * This characteristic indicates if a sensor detects abnormal levels of Carbon Monoxide. Value
 * should revert to 0 after the Carbon Monoxide levels drop to normal levels
 */
public class CarbonMonoxideDetectedCharacteristic
    extends EnumCharacteristic<CarbonMonoxideDetectedEnum> implements EventableCharacteristic {

  public CarbonMonoxideDetectedCharacteristic(
      Supplier<CompletableFuture<CarbonMonoxideDetectedEnum>> getter,
      Consumer<HomekitCharacteristicChangeCallback> subscriber,
      Runnable unsubscriber) {
    super(
        "00000069-0000-1000-8000-0026BB765291",
        "Carbon Monoxide Detected",
        1,
        Optional.of(getter),
        Optional.empty(),
        Optional.of(subscriber),
        Optional.of(unsubscriber));
  }
}
