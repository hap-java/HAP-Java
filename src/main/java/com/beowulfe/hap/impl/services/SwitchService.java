package com.beowulfe.hap.impl.services;

import com.beowulfe.hap.accessories.Switch;
import com.beowulfe.hap.impl.characteristics.common.PowerStateCharacteristic;

public class SwitchService extends AbstractServiceImpl {

	public SwitchService(Switch switchAccessory) {
		super("00000049-0000-1000-8000-0026BB765291", switchAccessory);
		addCharacteristic(new PowerStateCharacteristic(
				() -> switchAccessory.getSwitchState(),
				v -> switchAccessory.setSwitchState(v),
				c -> switchAccessory.subscribeSwitchState(c),
				() -> switchAccessory.unsubscribeSwitchState()
			));
	}

}