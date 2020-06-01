package io.github.hapjava.characteristics.impl.common;

import io.github.hapjava.characteristics.EventableCharacteristic;
import io.github.hapjava.characteristics.impl.base.IntegerCharacteristic;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

/**
 * This characteristic is used to identify the index of the label according to {@link
 * ServiceLabelNamespaceCharacteristic}
 */
public class ServiceLabelIndexCharacteristic extends IntegerCharacteristic
    implements EventableCharacteristic {

  public ServiceLabelIndexCharacteristic(Supplier<CompletableFuture<Integer>> getter) {
    super(
        "000000CB-0000-1000-8000-0026BB765291",
        "service label index",
        0,
        100,
        "%",
        Optional.of(getter),
        Optional.empty(),
        Optional.empty(),
        Optional.empty());
  }
}
