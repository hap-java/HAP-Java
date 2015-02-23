package com.beowulfe.hap.impl.services;

import com.beowulfe.hap.accessories.Thermostat;
import com.beowulfe.hap.impl.characteristics.common.Name;
import com.beowulfe.hap.impl.characteristics.thermostat.CoolingThresholdTemperatureCharacteristic;
import com.beowulfe.hap.impl.characteristics.thermostat.CurrentHeatingCoolingModeCharacteristic;
import com.beowulfe.hap.impl.characteristics.thermostat.CurrentTemperatureCharacteristic;
import com.beowulfe.hap.impl.characteristics.thermostat.HeatingThresholdTemperatureCharacteristic;
import com.beowulfe.hap.impl.characteristics.thermostat.TargetHeatingCoolingModeCharacteristic;
import com.beowulfe.hap.impl.characteristics.thermostat.TargetTemperatureCharacteristic;
import com.beowulfe.hap.impl.characteristics.thermostat.TemperatureUnitsCharacteristic;

public class ThermostatService extends AbstractServiceImpl {

	public ThermostatService(Thermostat thermostat) {
		super("0000004A-0000-1000-8000-0026BB765291");
		addCharacteristic(new Name(thermostat));
		addCharacteristic(new CurrentHeatingCoolingModeCharacteristic(thermostat));
		addCharacteristic(new CurrentTemperatureCharacteristic(thermostat));
		addCharacteristic(new TargetHeatingCoolingModeCharacteristic(thermostat));
		addCharacteristic(new TargetTemperatureCharacteristic(thermostat));
		addCharacteristic(new TemperatureUnitsCharacteristic(thermostat));
		addCharacteristic(new HeatingThresholdTemperatureCharacteristic(thermostat));
		addCharacteristic(new CoolingThresholdTemperatureCharacteristic(thermostat));
	}

}
