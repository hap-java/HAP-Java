package io.github.hapjava.accessories.optionalcharacteristic;

import java.util.concurrent.CompletableFuture;

/**
 * Accessory with name .
 *
 * @author Eugen Freiter
 */
public interface AccessoryWithHardwareRevision {

  /**
   * Returns a hardware revision to display in iOS.
   *
   * @return the hardware revision, or null.
   */
  CompletableFuture<String> getHardwareRevision();
}
