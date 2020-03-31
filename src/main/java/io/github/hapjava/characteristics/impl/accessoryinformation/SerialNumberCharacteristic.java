package io.github.hapjava.characteristics.impl.accessoryinformation;

import io.github.hapjava.characteristics.impl.base.StaticStringCharacteristic;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

/**
 * This characteristic contains the manufacturer-specific serial number of the accessory, e.g.
 * ”1A2B3C4D5E6F”. The length must be greater than 1.
 */
public class SerialNumberCharacteristic extends StaticStringCharacteristic {

  public SerialNumberCharacteristic(Supplier<CompletableFuture<String>> getter) {
    super(
        "00000030-0000-1000-8000-0026BB765291",
        "serial number",
        Optional.of(getter),
        Optional.empty(),
        Optional.empty());
  }
}
