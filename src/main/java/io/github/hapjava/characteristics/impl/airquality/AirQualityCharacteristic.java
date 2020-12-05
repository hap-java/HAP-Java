package io.github.hapjava.characteristics.impl.airquality;

import io.github.hapjava.characteristics.HomekitCharacteristicChangeCallback;
import io.github.hapjava.characteristics.impl.base.EnumCharacteristic;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * This characteristic describes the air quality. See {@link AirQualityEnum} for possible values.
 */
public class AirQualityCharacteristic extends EnumCharacteristic<AirQualityEnum> {

  public AirQualityCharacteristic(
      Supplier<CompletableFuture<AirQualityEnum>> getter,
      Consumer<HomekitCharacteristicChangeCallback> subscriber,
      Runnable unsubscriber) {
    super(
        "00000095-0000-1000-8000-0026BB765291",
        "air quality",
        AirQualityEnum.values(),
        Optional.of(getter),
        Optional.empty(),
        Optional.of(subscriber),
        Optional.of(unsubscriber));
  }
}
