package com.beowulfe.hap.impl.characteristics.thermostat;

import com.beowulfe.hap.accessories.Thermostat;
import com.beowulfe.hap.accessories.properties.TemperatureUnit;
import com.beowulfe.hap.characteristics.EventableCharacteristic;
import com.beowulfe.hap.characteristics.FloatCharacteristic;

public abstract class AbstractTemperatureCharacteristic extends FloatCharacteristic implements EventableCharacteristic {

	public AbstractTemperatureCharacteristic(String type, boolean isWritable, String description, Thermostat thermostat) {
		super(type, isWritable, true, description, thermostat.getMinimumTemperature(), thermostat.getMaximumTemperature(),
				0.1, thermostat.getTemperatureUnit() == TemperatureUnit.CELSIUS ? "celsius" : "fahrenheit");
	}

}
