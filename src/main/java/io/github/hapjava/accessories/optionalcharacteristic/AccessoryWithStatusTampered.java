package io.github.hapjava.accessories.optionalcharacteristic;

import io.github.hapjava.characteristics.HomekitCharacteristicChangeCallback;
import io.github.hapjava.characteristics.impl.common.StatusTamperedEnum;
import java.util.concurrent.CompletableFuture;

/**
 * This characteristic describes an accessory which has been tampered with. A status of 1 indicates
 * that the accessory has been tampered with. Value should return to 0 when the accessory has been
 * reset to a non-tampered state,
 *
 * @author Eugen Freiter
 */
public interface AccessoryWithStatusTampered {

  /**
   * Retrieves the status tampered. A status of 1 indicates that the accessory has been tampered
   * with. Value should return to 0 when the accessory has been reset to a non-tampered state,
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
