package io.github.hapjava.accessories.optionalcharacteristic;

import java.util.concurrent.CompletableFuture;

/** Accessory with filter reset characteristics */
public interface AccessoryWithResetFilterIndication {

  /**
   * Request to reset the filter level
   *
   * @param indication always 1, by HomeKit protocol. (to be ignored)
   * @return a future that completes when the change is made
   * @throws Exception when the change cannot be made
   */
  CompletableFuture<Void> resetFilterIndication(Integer indication) throws Exception;
}
