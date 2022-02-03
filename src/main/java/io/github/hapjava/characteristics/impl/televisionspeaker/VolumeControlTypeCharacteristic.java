package io.github.hapjava.characteristics.impl.televisionspeaker;

import io.github.hapjava.characteristics.HomekitCharacteristicChangeCallback;
import io.github.hapjava.characteristics.impl.base.EnumCharacteristic;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * This characteristic indicates volume control type. See {@link VolumeControlTypeEnum} for possible
 * values.
 */
public class VolumeControlTypeCharacteristic extends EnumCharacteristic<VolumeControlTypeEnum> {
  public VolumeControlTypeCharacteristic(
      Supplier<CompletableFuture<VolumeControlTypeEnum>> getter,
      Consumer<HomekitCharacteristicChangeCallback> subscriber,
      Runnable unsubscriber) {
    super(
        "000000E9-0000-1000-8000-0026BB765291",
        "volume control type",
        VolumeControlTypeEnum.values(),
        Optional.of(getter),
        Optional.empty(),
        Optional.of(subscriber),
        Optional.of(unsubscriber));
  }
}
