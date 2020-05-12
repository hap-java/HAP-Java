package io.github.hapjava.characteristics.impl.accessoryinformation;

import io.github.hapjava.characteristics.impl.base.StaticStringCharacteristic;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

/** This characteristic describes a hardware revision in a form x[.y[.z]] (e.g. ”100.1.1”) */
public class HardwareRevisionCharacteristic extends StaticStringCharacteristic {

  public HardwareRevisionCharacteristic(Supplier<CompletableFuture<String>> getter) {
    super(
        "00000053-0000-1000-8000-0026BB765291",
        "hardware revision",
        Optional.of(getter),
        Optional.empty(),
        Optional.empty());
  }
}
