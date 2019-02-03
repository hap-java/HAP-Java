package com.beowulfe.hap.accessories;

import com.beowulfe.hap.HomekitAccessory;
import com.beowulfe.hap.HomekitCharacteristicChangeCallback;
import com.beowulfe.hap.Service;
import com.beowulfe.hap.accessories.properties.ValveType;
import com.beowulfe.hap.impl.services.ValveService;
import java.util.Collection;
import java.util.Collections;
import java.util.concurrent.CompletableFuture;

/**
 * A Valve (sprinkler head, faucet, etc.)
 *
 * @author Tim Harper
 */
public interface Valve extends HomekitAccessory {

  @Override
  default Collection<Service> getServices() {
    return Collections.singleton(new ValveService(this));
  }

  /**
   * Retrieves the current active state of the valve; Active could mean the valve is open (but not
   * necessarily running), or that the valve is associated with an active watering program (like a
   * watering program) but is not currently running.
   *
   * <p>To communicate water is flowing through a valve, inUse should be used.
   *
   * @return a future that will contain the binary state
   */
  CompletableFuture<Boolean> getValveActive();

  /**
   * Sets the valve active state
   *
   * @param active the binary state to set
   * @return a future that completes when the change is made
   * @throws Exception when the change cannot be made
   */
  CompletableFuture<Void> setValveActive(boolean active) throws Exception;

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
  CompletableFuture<Boolean> getValveInUse();

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
   * <p>To communicate water is flowing through a valve, inUse should be used.
   *
   * @return a future that will contain the binary state
   */
  CompletableFuture<ValveType> getValveType();

  /**
   * Subscribes to changes in the valveType state of the valve.
   *
   * @param callback the function to call when the state changes.
   */
  void subscribeValveType(HomekitCharacteristicChangeCallback callback);

  /** Unsubscribes from changes in the valveType state light. */
  void unsubscribeValveType();
}
