package io.github.hapjava.accessories.optionalcharacteristic;

import java.util.concurrent.CompletableFuture;

/**
 * Accessory with identifier characteristic {@link
 * io.github.hapjava.characteristics.impl.common.IdentifierCharacteristic}.
 */
public interface AccessoryWithIdentifier {

  /**
   * Retrieves the identifier of service.
   *
   * @return a future with the identifier
   */
  CompletableFuture<Integer> getIdentifier();
}
