package com.beowulfe.hap.impl.characteristics.thermostat;

import java.util.concurrent.CompletableFuture;

import com.beowulfe.hap.HomekitCharacteristicChangeCallback;
import com.beowulfe.hap.accessories.properties.ThermostatMode;
import com.beowulfe.hap.accessories.thermostat.BasicThermostat;

public class TargetHeatingCoolingModeCharacteristic extends
		AbstractHeatingCoolingModeCharacteristic {

	private final BasicThermostat thermostat;
	
	public TargetHeatingCoolingModeCharacteristic(BasicThermostat thermostat) {
		super("00000033-0000-1000-8000-0026BB765291", false, "Target Mode");
		this.thermostat = thermostat;
	}

	@Override
	protected void setModeValue(ThermostatMode mode) throws Exception {
		thermostat.setTargetMode(mode);
	}

	@Override
	protected CompletableFuture<ThermostatMode> getModeValue() {
		return thermostat.getTargetMode();
	}

	@Override
	public void subscribe(HomekitCharacteristicChangeCallback callback) {
		thermostat.subscribeTargetMode(callback);
	}

	@Override
	public void unsubscribe() {
		thermostat.unsubscribeTargetMode();
	}

}
