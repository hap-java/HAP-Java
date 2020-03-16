package io.github.hapjava.accessories.optionalcharacteristic;

import java.util.concurrent.CompletableFuture;

/**
 * Accessory with service label index. This characteristic should be used identify the index of the
 * label that maps to ”Service Label Namespace” used by the accessory.
 *
 * @author Eugen Freiter
 */
public interface AccessoryWithServiceLabelIndex {

  /**
   * Retrieves the service label index.
   *
   * @return a future with the service label index
   */
  CompletableFuture<Integer> getServiceLabelIndex();
}
