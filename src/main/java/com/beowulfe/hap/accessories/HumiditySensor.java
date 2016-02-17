package com.beowulfe.hap.accessories;

import java.util.Collection;
import java.util.Collections;
import java.util.concurrent.CompletableFuture;

import com.beowulfe.hap.*;
import com.beowulfe.hap.impl.services.HumiditySensorService;

/**
 * A humidity sensor that reports the current relative humidity.
 *
 * @author Andy Lintner
 */
public interface HumiditySensor extends HomekitAccessory {

	/**
	 * Retrieves the current relative humidity.
	 * @return a future that will contain the humidity as a value between 0 and 100
	 */
	CompletableFuture<Double> getCurrentRelativeHumidity();
	
	@Override
	default Collection<Service> getServices() {
		return Collections.singleton(new HumiditySensorService(this));
	}
	
	/**
	 * Subscribes to changes in the current relative humidity.
	 * @param callback the function to call when the state changes.
	 */
	void subscribeCurrentRelativeHumidity(HomekitCharacteristicChangeCallback callback);

	/**
	 * Unsubscribes from changes in the current relative humidity.
	 */
	void unsubscribeCurrentRelativeHumidity();
	
}
