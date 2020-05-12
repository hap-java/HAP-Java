package io.github.hapjava.characteristics.impl.common;

import io.github.hapjava.characteristics.impl.base.StaticStringCharacteristic;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

/** This characteristic describes HAP version. */
public class VersionCharacteristic extends StaticStringCharacteristic {

  public VersionCharacteristic(Supplier<CompletableFuture<String>> getter) {
    super(
        "00000023-0000-1000-8000-0026BB765291",
        "HAP version",
        Optional.of(getter),
        Optional.empty(),
        Optional.empty());
  }
}
