package io.github.hapjava.accessories.optionalcharacteristic;

import java.util.concurrent.CompletableFuture;

/**
 * Accessory with name.
 *
 * @author Eugen Freiter
 */
public interface AccessoryWithName {

  /**
   * Retrieves the name of service.
   *
   * @return a future with the name
   */
  CompletableFuture<String> getName();
}
