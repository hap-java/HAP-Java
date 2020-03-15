package io.github.hapjava.accessories;

import io.github.hapjava.accessories.optionalcharacteristic.AccessoryWithFanState;
import io.github.hapjava.characteristics.HomekitCharacteristicChangeCallback;
import io.github.hapjava.services.Service;
import io.github.hapjava.services.impl.FanService;
import java.util.Collection;
import java.util.Collections;
import java.util.concurrent.CompletableFuture;

/**
 * A fan with basic characteristics. User {@link AccessoryWithFanState} for Fan with more
 * characteristics.
 *
 * @author Andy Lintner
 */
public interface FanAccessory extends HomekitAccessory {
  /**
   * Mandatory: Retrieves the current active state of the fan'.
   *
   * @return a future that will contain the binary state
   */
  CompletableFuture<Boolean> isActive();

  /**
   * Sets the active state of the fan
   *
   * @param state the binary state to set
   * @return a future that completes when the change is made
   * @throws Exception when the change cannot be made
   */
  CompletableFuture<Void> setActive(boolean state) throws Exception;

  /**
   * Subscribes to changes in the active state of the fan.
   *
   * @param callback the function to call when the direction changes.
   */
  void subscribeActive(HomekitCharacteristicChangeCallback callback);

  /** Unsubscribes from changes in the active state of the fan. */
  void unsubscribeActive();

  @Override
  default Collection<Service> getServices() {
    return Collections.singleton(new FanService(this));
  }
}
