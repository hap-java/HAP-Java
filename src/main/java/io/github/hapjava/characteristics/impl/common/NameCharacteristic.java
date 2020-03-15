package io.github.hapjava.characteristics.impl.common;

import io.github.hapjava.characteristics.impl.base.StaticStringCharacteristic;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

/** This characteristic describes a name and must not be a null value. */
public class NameCharacteristic extends StaticStringCharacteristic {

  public NameCharacteristic(Supplier<CompletableFuture<String>> getter) {
    super(
        "00000023-0000-1000-8000-0026BB765291",
        "name of the accessory",
        Optional.of(getter),
        Optional.empty(),
        Optional.empty());
  }
}
