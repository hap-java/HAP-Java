package com.beowulfe.hap.impl.services;

import com.beowulfe.hap.accessories.Fan;
import com.beowulfe.hap.impl.characteristics.common.PowerStateCharacteristic;
import com.beowulfe.hap.impl.characteristics.fan.RotationDirectionCharacteristic;
import com.beowulfe.hap.impl.characteristics.fan.RotationSpeedCharacteristic;

public class FanService extends AbstractServiceImpl {

	public FanService(Fan fan) {
		this(fan, fan.getLabel());
	}

	public FanService(Fan fan, String serviceName) {
		super("00000040-0000-1000-8000-0026BB765291", fan, serviceName);
		addCharacteristic(new PowerStateCharacteristic(
				() -> fan.getFanPower(),
				v -> fan.setFanPower(v),
				c -> fan.subscribeFanPower(c),
				() -> fan.unsubscribeFanPower()
			));
		addCharacteristic(new RotationDirectionCharacteristic(fan));
		addCharacteristic(new RotationSpeedCharacteristic(fan));
	}

}