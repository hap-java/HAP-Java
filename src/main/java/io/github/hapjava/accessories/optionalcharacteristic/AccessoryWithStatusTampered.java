package io.github.hapjava.accessories.optionalcharacteristic;

import io.github.hapjava.characteristics.HomekitCharacteristicChangeCallback;
import io.github.hapjava.characteristics.impl.common.StatusTamperedEnum;
import java.util.concurrent.CompletableFuture;

/** This characteristic describes an accessory which has been tampered with. */
public interface AccessoryWithStatusTampered {

  /**
   * Retrieves the status tampered.
   *
   * @return a future with the value
   */
  CompletableFuture<StatusTamperedEnum> getStatusTampered();

  /**
   * Subscribes to changes in status tampered.
   *
   * @param callback the function when the status tampered changes
   */
  void subscribeStatusTampered(HomekitCharacteristicChangeCallback callback);

  /** Unsubscribes from changes */
  void unsubscribeStatusTampered();
}
