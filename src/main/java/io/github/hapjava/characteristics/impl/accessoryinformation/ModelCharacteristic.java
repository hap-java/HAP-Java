package io.github.hapjava.characteristics.impl.accessoryinformation;

import io.github.hapjava.characteristics.impl.base.StaticStringCharacteristic;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

/** This characteristic contains the manufacturer-specific model of the accessory */
public class ModelCharacteristic extends StaticStringCharacteristic {

  public ModelCharacteristic(Supplier<CompletableFuture<String>> getter) {
    super(
        "00000021-0000-1000-8000-0026BB765291",
        "model",
        Optional.of(getter),
        Optional.empty(),
        Optional.empty());
  }
}
