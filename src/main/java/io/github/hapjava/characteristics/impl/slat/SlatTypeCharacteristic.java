package io.github.hapjava.characteristics.impl.slat;

import io.github.hapjava.characteristics.impl.base.EnumCharacteristic;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

/**
 * TThis characteristic describes the type of the slats. If the slats can tilt on a horizontal axis,
 * the value of this characteristic must be set to Horizontal. If the slats can tilt on a vertical
 * axis, the value of this characteristic must be set to Vertical.
 */
public class SlatTypeCharacteristic extends EnumCharacteristic<SlatTypeEnum> {

  public SlatTypeCharacteristic(Supplier<CompletableFuture<SlatTypeEnum>> getter) {
    super(
        "000000C0-0000-1000-8000-0026BB765291",
        "slat type",
        1,
        Optional.of(getter),
        Optional.empty(),
        Optional.empty(),
        Optional.empty());
  }
}
