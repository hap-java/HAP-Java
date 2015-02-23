package com.beowulfe.hap.accessories;

import java.util.Collection;
import java.util.Collections;
import java.util.concurrent.CompletableFuture;

import com.beowulfe.hap.HomekitAccessory;
import com.beowulfe.hap.HomekitCharacteristicChangeCallback;
import com.beowulfe.hap.Service;
import com.beowulfe.hap.accessories.properties.LockMechanismState;
import com.beowulfe.hap.impl.services.LockMechanismService;

/**
 * A lock capable of exposing its binary locked state. For a lock that can be locked/unlocked, use
 * {@link LockableLockMechanism}
 *
 * @author Andy Lintner
 */
public interface LockMechanism extends HomekitAccessory {

	/**
	 * Retrieves the current binary state of the lock.
	 * @return a future that will contain the binary state.
	 */
	CompletableFuture<LockMechanismState> getCurrentMechanismState();

	/**
	 * Subscribes to changes in the binary state of the lock.
	 * @param callback the function to call when the state changes.
	 */
	void subscribeCurrentMechanismState(HomekitCharacteristicChangeCallback callback);

	/**
	 * Unsubscribes from changes in the binary state of the lock.
	 */
	void unsubscribeCurrentMechanismState();

	@Override
	default public Collection<Service> getServices() {
		return Collections.singleton(new LockMechanismService(this));
	}
}
