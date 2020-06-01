package io.github.hapjava.accessories;

import io.github.hapjava.characteristics.HomekitCharacteristicChangeCallback;
import io.github.hapjava.services.Service;
import io.github.hapjava.services.impl.FaucetService;
import java.util.Collection;
import java.util.Collections;
import java.util.concurrent.CompletableFuture;

/** Faucets or shower heads accessory. */
public interface FaucetAccessory extends HomekitAccessory {
  /**
   * Mandatory: Retrieves the current active state of the faucet.
   *
   * @return a future that will contain the binary state
   */
  CompletableFuture<Boolean> isActive();

  /**
   * Sets the active state of the faucet
   *
   * @param state the binary state to set
   * @return a future that completes when the change is made
   * @throws Exception when the change cannot be made
   */
  CompletableFuture<Void> setActive(boolean state) throws Exception;

  /**
   * Subscribes to changes in the active state of the faucet.
   *
   * @param callback the function to call when the active state changes.
   */
  void subscribeActive(HomekitCharacteristicChangeCallback callback);

  /** Unsubscribes from changes in the active state of the faucet. */
  void unsubscribeActive();

  @Override
  default Collection<Service> getServices() {
    return Collections.singleton(new FaucetService(this));
  }
}
