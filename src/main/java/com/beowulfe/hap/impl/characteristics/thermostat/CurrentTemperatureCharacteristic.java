package com.beowulfe.hap.impl.characteristics.thermostat;

import java.util.concurrent.CompletableFuture;

import com.beowulfe.hap.HomekitCharacteristicChangeCallback;
import com.beowulfe.hap.accessories.TemperatureSensor;

public class CurrentTemperatureCharacteristic extends
		AbstractTemperatureCharacteristic {

	private final TemperatureSensor sensor;
	
	public CurrentTemperatureCharacteristic(TemperatureSensor thermostat) {
		super("00000011-0000-1000-8000-0026BB765291", false, "Current Temperature", thermostat);
		this.sensor = thermostat;
	}

	@Override
	public void subscribe(HomekitCharacteristicChangeCallback callback) {
		sensor.subscribeCurrentTemperature(callback);
	}

	@Override
	public void unsubscribe() {
		sensor.unsubscribeCurrentTemperature();
	}

	@Override
	protected CompletableFuture<Double> getDoubleValue() {
		return sensor.getCurrentTemperature();
	}

	@Override
	protected void setValue(Double value) throws Exception {
		//Not writable
	}

}
