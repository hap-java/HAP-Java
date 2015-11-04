package com.beowulfe.hap.impl.characteristics.thermostat;

import com.beowulfe.hap.accessories.TemperatureSensor;
import com.beowulfe.hap.accessories.properties.TemperatureUnit;
import com.beowulfe.hap.characteristics.EventableCharacteristic;
import com.beowulfe.hap.characteristics.FloatCharacteristic;

public abstract class AbstractTemperatureCharacteristic extends FloatCharacteristic implements EventableCharacteristic {

	public AbstractTemperatureCharacteristic(String type, boolean isWritable, String description, TemperatureSensor sensor) {
		super(type, isWritable, true, description, sensor.getMinimumTemperature(), sensor.getMaximumTemperature(),
				0.1, sensor.getTemperatureUnit() == TemperatureUnit.CELSIUS ? "celsius" : "fahrenheit");
	}

}
