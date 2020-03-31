package io.github.hapjava.characteristics.impl.accessoryinformation;

import io.github.hapjava.characteristics.impl.base.StaticStringCharacteristic;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

/**
 * This characteristic contains the name of the company whose brand will appear on the accessory,
 * e.g., ”Acme”.
 */
public class ManufacturerCharacteristic extends StaticStringCharacteristic {

  public ManufacturerCharacteristic(Supplier<CompletableFuture<String>> getter) {
    super(
        "00000020-0000-1000-8000-0026BB765291",
        "manufacturer",
        Optional.of(getter),
        Optional.empty(),
        Optional.empty());
  }
}
