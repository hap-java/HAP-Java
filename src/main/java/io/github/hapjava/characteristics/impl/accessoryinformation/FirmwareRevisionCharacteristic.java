package io.github.hapjava.characteristics.impl.accessoryinformation;

import io.github.hapjava.characteristics.impl.base.StaticStringCharacteristic;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

/** This characteristic describes a firmware revision string */
public class FirmwareRevisionCharacteristic extends StaticStringCharacteristic {

  public FirmwareRevisionCharacteristic(Supplier<CompletableFuture<String>> getter) {
    super(
        "00000052-0000-1000-8000-0026BB765291",
        "firmware revision",
        Optional.of(getter),
        Optional.empty(),
        Optional.empty());
  }
}
