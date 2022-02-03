package io.github.hapjava.characteristics.impl.common;

import io.github.hapjava.characteristics.impl.base.IntegerCharacteristic;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

public class IdentifierCharacteristic extends IntegerCharacteristic {

  public IdentifierCharacteristic(Supplier<CompletableFuture<Integer>> getter) {
    super(
        "000000E6-0000-1000-8000-0026BB765291",
        "identifier",
        0,
        1000,
        "",
        Optional.of(getter),
        Optional.empty(),
        Optional.empty(),
        Optional.empty());
  }
}
