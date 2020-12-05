package io.github.hapjava.characteristics.impl.carbonmonoxidesensor;

import io.github.hapjava.characteristics.EventableCharacteristic;
import io.github.hapjava.characteristics.HomekitCharacteristicChangeCallback;
import io.github.hapjava.characteristics.impl.base.EnumCharacteristic;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * This characteristic indicates the levels of Carbon Monoxide. See {@link
 * CarbonMonoxideDetectedEnum} for possible values
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
        CarbonMonoxideDetectedEnum.values(),
        Optional.of(getter),
        Optional.empty(),
        Optional.of(subscriber),
        Optional.of(unsubscriber));
  }
}
