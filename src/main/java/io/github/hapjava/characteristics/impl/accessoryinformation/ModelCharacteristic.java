package io.github.hapjava.characteristics.impl.accessoryinformation;

import io.github.hapjava.characteristics.impl.base.StaticStringCharacteristic;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

/**
 * This characteristic contains the manufacturer-specific model of the accessory, e.g. ”A1234”. The
 * minimum length of this characteristic must be 1.
 */
public class ModelCharacteristic extends StaticStringCharacteristic {

  public ModelCharacteristic(Supplier<CompletableFuture<String>> getter) {
    super(
        "00000021-0000-1000-8000-0026BB765291",
        "name of the model",
        Optional.of(getter),
        Optional.empty(),
        Optional.empty());
  }
}
