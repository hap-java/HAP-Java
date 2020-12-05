package io.github.hapjava.characteristics.impl.slat;

import io.github.hapjava.characteristics.impl.base.EnumCharacteristic;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

/**
 * This characteristic describes the type of the slats. See {@link SlatTypeEnum} for possible values
 */
public class SlatTypeCharacteristic extends EnumCharacteristic<SlatTypeEnum> {

  public SlatTypeCharacteristic(Supplier<CompletableFuture<SlatTypeEnum>> getter) {
    super(
        "000000C0-0000-1000-8000-0026BB765291",
        "slat type",
        SlatTypeEnum.values(),
        Optional.of(getter),
        Optional.empty(),
        Optional.empty(),
        Optional.empty());
  }
}
