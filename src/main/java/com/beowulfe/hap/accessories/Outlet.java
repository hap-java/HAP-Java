package com.beowulfe.hap.accessories;

import java.util.Collection;
import java.util.Collections;
import java.util.concurrent.CompletableFuture;

import com.beowulfe.hap.HomekitAccessory;
import com.beowulfe.hap.HomekitCharacteristicChangeCallback;
import com.beowulfe.hap.Service;
import com.beowulfe.hap.impl.services.OutletService;

/**
 * A power outlet with boolean power and usage states.
 *
 * @author Andy Lintner
 */
public interface Outlet extends HomekitAccessory {

	@Override
	default public Collection<Service> getServices() {
		return Collections.singleton(new OutletService(this));
	}

	/**
	 * Retrieves the current binary state of the outlet's power.
	 * @return a future that will contain the binary state
	 */
	public CompletableFuture<Boolean> getPowerState();
	
	/**
	 * Retrieves the current binary state indicating whether the outlet is in use.
	 * @return a future that will contain the binary state
	 */
	public CompletableFuture<Boolean> getOutletInUse();
	
	/**
	 * Sets the binary state of the outlet's power.
	 * @param state the binary state to set
	 * @return a future that completes when the change is made
	 * @throws Exception when the change cannot be made
	 */
	public CompletableFuture<Void> setPowerState(boolean state);
	
	/**
	 * Subscribes to changes in the binary state of the outlet's power.
	 * @param callback the function to call when the state changes.
	 */
	public void subscribePowerState(HomekitCharacteristicChangeCallback callback);

	/**
	 * Subscribes to changes in the binary state indicating whether the outlet is in use.
	 * @param callback the function to call when the state changes.
	 */
	public void subscribeOutletInUse(HomekitCharacteristicChangeCallback callback);
	
	/**
	 * Unsubscribes from changes in the binary state of the outlet's power.
	 */
	public void unsubscribePowerState();

	/**
	 * Unsubscribes from changes in the binary state indicating whether hte outlet is in use.
	 */
	public void unsubscribeOutletInUse();

}
