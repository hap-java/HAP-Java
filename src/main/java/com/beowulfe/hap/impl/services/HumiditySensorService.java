package com.beowulfe.hap.impl.services;

import com.beowulfe.hap.accessories.HumiditySensor;
import com.beowulfe.hap.impl.characteristics.humiditysensor.CurrentRelativeHumidityCharacteristic;

public class HumiditySensorService extends AbstractServiceImpl {
	
	public HumiditySensorService(HumiditySensor sensor) {
		this(sensor, sensor.getLabel());
	}

	public HumiditySensorService(HumiditySensor sensor, String serviceName) {
		super("00000082-0000-1000-8000-0026BB765291", sensor, serviceName);
		addCharacteristic(new CurrentRelativeHumidityCharacteristic(sensor));
	}

}