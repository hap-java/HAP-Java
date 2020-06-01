package io.github.hapjava.accessories.optionalcharacteristic;

import java.util.concurrent.CompletableFuture;

/** Accessory with service label index. T */
public interface AccessoryWithServiceLabelIndex {

  /**
   * Retrieves the service label index.
   *
   * @return a future with the service label index
   */
  CompletableFuture<Integer> getServiceLabelIndex();
}
