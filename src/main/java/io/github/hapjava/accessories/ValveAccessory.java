package io.github.hapjava.accessories;

import io.github.hapjava.characteristics.HomekitCharacteristicChangeCallback;
import io.github.hapjava.characteristics.impl.common.ActiveEnum;
import io.github.hapjava.characteristics.impl.common.InUseEnum;
import io.github.hapjava.characteristics.impl.valve.ValveTypeEnum;
import io.github.hapjava.services.Service;
import io.github.hapjava.services.impl.ValveService;
import java.util.Collection;
import java.util.Collections;
import java.util.concurrent.CompletableFuture;

/**
 * A Valve (sprinkler head, faucet, etc.)
 *
 * @author Tim Harper
 */
public interface ValveAccessory extends HomekitAccessory {

  /**
   * Retrieves the current active state of the valve; Active could mean the valve is open (but not
   * necessarily running), or that the valve is associated with an active watering program (like a
   * watering program) but is not currently running.
   *
   * <p>To communicate water is flowing through a valve, inUse should be used.
   *
   * @return a future that will contain the binary state
   */
  CompletableFuture<ActiveEnum> getValveActive();

  /**
   * Sets the valve active state
   *
   * @param active the binary state to set
   * @return a future that completes when the change is made
   * @throws Exception when the change cannot be made
   */
  CompletableFuture<Void> setValveActive(ActiveEnum active) throws Exception;

  /**
   * Subscribes to changes in the active state of the valve.
   *
   * @param callback the function to call when the state changes.
   */
  void subscribeValveActive(HomekitCharacteristicChangeCallback callback);

  /** Unsubscribes from changes in the valve active state. */
  void unsubscribeValveActive();

  /**
   * Retrieves the current inUse state of the valve; InUse usually means water is flowing through
   * the valve.
   *
   * <p>To communicate water is flowing through a valve, inUse should be used.
   *
   * @return a future that will contain the binary state
   */
  CompletableFuture<InUseEnum> getValveInUse();

  /**
   * Subscribes to changes in the inUse state of the valve.
   *
   * @param callback the function to call when the state changes.
   */
  void subscribeValveInUse(HomekitCharacteristicChangeCallback callback);

  /** Unsubscribes from changes in the valve inUse state. */
  void unsubscribeValveInUse();

  /**
   * Retrieves the valve type.
   *
   * @return a future that will contain the valve type.
   */
  CompletableFuture<ValveTypeEnum> getValveType();

  /**
   * Subscribes to changes in the valveType state of the valve.
   *
   * @param callback the function to call when the state changes.
   */
  void subscribeValveType(HomekitCharacteristicChangeCallback callback);

  /** Unsubscribes from changes in the valveType state light. */
  void unsubscribeValveType();

  @Override
  default Collection<Service> getServices() {
    return Collections.singleton(new ValveService(this));
  }
}
