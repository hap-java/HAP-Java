package com.beowulfe.hap.impl.characteristics.thermostat;

import java.util.concurrent.CompletableFuture;

import com.beowulfe.hap.HomekitCharacteristicChangeCallback;
import com.beowulfe.hap.accessories.Thermostat;

public class CurrentTemperatureCharacteristic extends
		AbstractTemperatureCharacteristic {

	private final Thermostat thermostat;
	
	public CurrentTemperatureCharacteristic(Thermostat thermostat) {
		super("00000011-0000-1000-8000-0026BB765291", false, "Current Temperature", thermostat);
		this.thermostat = thermostat;
	}

	@Override
	public void subscribe(HomekitCharacteristicChangeCallback callback) {
		thermostat.subscribeCurrentTemperature(callback);
	}

	@Override
	public void unsubscribe() {
		thermostat.unsubscribeCurrentTemperature();
	}

	@Override
	protected CompletableFuture<Double> getDoubleValue() {
		return thermostat.getCurrentTemperature();
	}

	@Override
	protected void setValue(Double value) throws Exception {
		//Not writable
	}

}
