package com.beowulfe.hap.accessories;

import java.util.Collection;
import java.util.Collections;
import java.util.concurrent.CompletableFuture;

import com.beowulfe.hap.*;
import com.beowulfe.hap.accessories.properties.RotationDirection;
import com.beowulfe.hap.impl.services.FanService;

/**
 * A fan, with power and rotational characteristics.
 *
 * @author Andy Lintner
 */
public interface Fan extends HomekitAccessory {

	/**
	 * Retrieves the current binary state of the fan's power.
	 * @return a future that will contain the binary state
	 */
	CompletableFuture<Boolean> getFanPower();
	
	/**
	 * Retrieves the current rotation direction of the fan.
	 * @return a future that will contain the direction
	 */
	CompletableFuture<RotationDirection> getRotationDirection();
	
	/**
	 * Retrieves the current speed of the fan's rotation
	 * @return a future that will contain the speed, expressed as an integer between 0 and 100.
	 */
	CompletableFuture<Integer> getRotationSpeed();
	
	/**
	 * Sets the binary state of the fan's power
	 * @param state the binary state to set
	 * @return a future that completes when the change is made
	 * @throws Exception when the change cannot be made
	 */
	CompletableFuture<Void> setFanPower(boolean state) throws Exception;
	
	/**
	 * Sets the rotation direction of the fan
	 * @param direction the direction to set
	 * @return a future that completes when the change is made
	 * @throws Exception when the change cannot be made
	 */
	CompletableFuture<Void> setRotationDirection(RotationDirection direction) throws Exception;
	
	
	/**
	 * Sets the speed of the fan's rotation
	 * @param speed the speed to set, expressed as an integer between 0 and 100.
	 * @return a future that completes when the change is made
	 * @throws Exception when the change cannot be made
	 */
	CompletableFuture<Void> setRotationSpeed(Integer speed) throws Exception;
	
	@Override
	default Collection<Service> getServices() {
		return Collections.singleton(new FanService(this));
	}
	
	/**
	 * Subscribes to changes in the binary state of the fan's power.
	 * @param callback the function to call when the state changes.
	 */
	void subscribeFanPower(HomekitCharacteristicChangeCallback callback);
	
	/**
	 * Subscribes to changes in the rotation direction of the fan.
	 * @param callback the function to call when the direction changes.
	 */
	void subscribeRotationDirection(HomekitCharacteristicChangeCallback callback);
	
	/**
	 * Subscribes to changes in the rotation speed of the fan.
	 * @param callback the function to call when the speed changes.
	 */
	void subscribeRotationSpeed(HomekitCharacteristicChangeCallback callback);
	
	/**
	 * Unsubscribes from changes in the binary state of the fan's power.
	 */
	void unsubscribeFanPower();
	
	/**
	 * Unsubscribes from changes in the rotation direction of the fan.
	 */
	void unsubscribeRotationDirection();
	
	/**
	 * Unsubscribes from changes in the fan's rotation speed.
	 */
	void unsubscribeRotationSpeed();
}
