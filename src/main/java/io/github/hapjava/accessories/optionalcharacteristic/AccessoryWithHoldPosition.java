package io.github.hapjava.accessories.optionalcharacteristic;

import java.util.concurrent.CompletableFuture;

/** Accessory with hold position */
public interface AccessoryWithHoldPosition {

  /**
   * Sets the hold position state
   *
   * @param hold whether or not to hold the current position state
   * @return a future that completes when the change is made
   * @throws Exception when the change cannot be made
   */
  CompletableFuture<Void> setHoldPosition(boolean hold) throws Exception;
}
