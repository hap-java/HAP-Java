package io.github.hapjava.accessories;

import io.github.hapjava.characteristics.HomekitCharacteristicChangeCallback;
import io.github.hapjava.services.Service;
import io.github.hapjava.services.impl.BasicFanService;
import java.util.Collection;
import java.util.Collections;
import java.util.concurrent.CompletableFuture;

/**
 * A fan with mandatory characteristics.
 *
 * @author Andy Lintner
 */
public interface BasicFanAccessory extends HomekitAccessory {
  /**
   * Mandatory: Retrieves the current power state of the fan.
   *
   * @return a future that will contain the binary state
   */
  CompletableFuture<Boolean> isOn();

  /**
   * Sets the active state of the fan
   *
   * @param state the binary state to set
   * @return a future that completes when the change is made
   * @throws Exception when the change cannot be made
   */
  CompletableFuture<Void> setOn(boolean state) throws Exception;

  /**
   * Subscribes to changes in the active state of the fan.
   *
   * @param callback the function to call when the active state changes.
   */
  void subscribeOn(HomekitCharacteristicChangeCallback callback);

  /** Unsubscribes from changes in the active state of the fan. */
  void unsubscribeOn();

  @Override
  default Collection<Service> getServices() {
    return Collections.singleton(new BasicFanService(this));
  }
}
