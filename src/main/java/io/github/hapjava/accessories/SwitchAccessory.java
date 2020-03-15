package io.github.hapjava.accessories;

import io.github.hapjava.characteristics.HomekitCharacteristicChangeCallback;
import io.github.hapjava.services.Service;
import io.github.hapjava.services.impl.SwitchService;
import java.util.Collection;
import java.util.Collections;
import java.util.concurrent.CompletableFuture;

/**
 * A simple switch with a binary state.
 *
 * @author Andy Lintner
 */
public interface SwitchAccessory extends HomekitAccessory {

  /**
   * Retrieves the current binary state of the switch.
   *
   * @return a future that will contain the binary state
   */
  CompletableFuture<Boolean> getSwitchState();

  /**
   * Sets the binary state of the switch
   *
   * @param state the binary state to set
   * @return a future that completes when the change is made
   * @throws Exception when the change cannot be made
   */
  CompletableFuture<Void> setSwitchState(boolean state) throws Exception;

  /**
   * Subscribes to changes in the binary state of the switch.
   *
   * @param callback the function to call when the state changes.
   */
  void subscribeSwitchState(HomekitCharacteristicChangeCallback callback);

  /** Unsubscribes from changes in the binary state of the switch. */
  void unsubscribeSwitchState();

  @Override
  default Collection<Service> getServices() {
    return Collections.singleton(new SwitchService(this));
  }
}
