package com.beowulfe.hap.accessories.thermostat;

import java.util.concurrent.CompletableFuture;

import com.beowulfe.hap.HomekitCharacteristicChangeCallback;

public interface HeatingThermostat extends BasicThermostat {

	/**
	 * Retrieves the temperature below which the thermostat should begin heating. 
	 * @return a future that will contain the threshold temperature, in celsius degrees. 
	 */
	CompletableFuture<Double> getHeatingThresholdTemperature();

	/**
	 * Sets the temperature below which the thermostat should begin heating. 
	 * @param value the threshold temperature, in celsius degrees.
	 * @throws Exception when the threshold temperature cannot be changed.
	 */
	void setHeatingThresholdTemperature(Double value) throws Exception;
	
	/**
	 * Subscribes to changes in the heating threshold.
	 * @param callback the function to call when the state changes.
	 */
	void subscribeHeatingThresholdTemperature(HomekitCharacteristicChangeCallback callback);

	/**
	 * Unsubscribes from changes in the heating threshold.
	 */
	void unsubscribeHeatingThresholdTemperature();
}
