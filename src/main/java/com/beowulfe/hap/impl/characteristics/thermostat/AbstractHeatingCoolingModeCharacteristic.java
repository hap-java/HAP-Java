package com.beowulfe.hap.impl.characteristics.thermostat;

import java.util.concurrent.CompletableFuture;

import com.beowulfe.hap.accessories.properties.ThermostatMode;
import com.beowulfe.hap.characteristics.EnumCharacteristic;
import com.beowulfe.hap.characteristics.EventableCharacteristic;

abstract class AbstractHeatingCoolingModeCharacteristic extends EnumCharacteristic implements EventableCharacteristic {

	public AbstractHeatingCoolingModeCharacteristic(String type, boolean isWritable, String description) {
		super(type, isWritable, true, description, 3);
	}

	@Override
	protected final void setValue(Integer value) throws Exception {
		setModeValue(ThermostatMode.fromCode(value));
	}

	@Override
	protected final CompletableFuture<Integer> getValue() {
		return getModeValue().thenApply(t -> t.getCode());
	}

	protected abstract void setModeValue(ThermostatMode mode) throws Exception;
	
	protected abstract CompletableFuture<ThermostatMode> getModeValue();
	
	
}
