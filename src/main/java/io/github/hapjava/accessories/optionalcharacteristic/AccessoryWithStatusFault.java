package io.github.hapjava.accessories.optionalcharacteristic;

import io.github.hapjava.characteristics.HomekitCharacteristicChangeCallback;
import io.github.hapjava.characteristics.impl.common.StatusFaultEnum;
import java.util.concurrent.CompletableFuture;

/**
 * Accessory with characteristic that describes an accessory which has a fault. A non-zero value
 * indicates that the accessory has experienced a fault that may be interfering with its intended
 * functionality. A value of 0 indicates that there is no fault.
 *
 * @author Eugen Freiter
 */
public interface AccessoryWithStatusFault {

  /**
   * Retrieves the status fault. A non-zero value indicates that the accessory has experienced a
   * fault that may be interfering with its intended functionality. A value of 0 indicates that
   * there is no fault.
   *
   * @return a future with the value
   */
  CompletableFuture<StatusFaultEnum> getStatusFault();

  /**
   * Subscribes to changes in status fault.
   *
   * @param callback the function when the status fault changes
   */
  void subscribeStatusFault(HomekitCharacteristicChangeCallback callback);

  /** Unsubscribes from changes */
  void unsubscribeStatusFault();
}
