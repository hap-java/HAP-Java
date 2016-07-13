package com.beowulfe.hap.impl.services;

import com.beowulfe.hap.accessories.TemperatureSensor;
import com.beowulfe.hap.impl.characteristics.thermostat.CurrentTemperatureCharacteristic;

public class TemperatureSensorService extends AbstractServiceImpl {
	
	public TemperatureSensorService(TemperatureSensor sensor) {
		super("0000008A-0000-1000-8000-0026BB765291", sensor);
		addCharacteristic(new CurrentTemperatureCharacteristic(sensor));
	}

}
