package io.github.hapjava.characteristics.impl.carbondioxidesensor;

import io.github.hapjava.characteristics.EventableCharacteristic;
import io.github.hapjava.characteristics.HomekitCharacteristicChangeCallback;
import io.github.hapjava.characteristics.impl.base.EnumCharacteristic;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * This characteristic indicates the levels of Carbon Dioxide. See {@link CarbonDioxideDetectedEnum}
 * for possible values.
 */
public class CarbonDioxideDetectedCharacteristic
    extends EnumCharacteristic<CarbonDioxideDetectedEnum> implements EventableCharacteristic {

  public CarbonDioxideDetectedCharacteristic(
      Supplier<CompletableFuture<CarbonDioxideDetectedEnum>> getter,
      Consumer<HomekitCharacteristicChangeCallback> subscriber,
      Runnable unsubscriber) {
    super(
        "00000092-0000-1000-8000-0026BB765291",
        "Carbon Dioxide Detected",
        CarbonDioxideDetectedEnum.values(),
        Optional.of(getter),
        Optional.empty(),
        Optional.of(subscriber),
        Optional.of(unsubscriber));
  }
}
