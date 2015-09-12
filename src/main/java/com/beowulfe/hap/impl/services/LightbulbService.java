package com.beowulfe.hap.impl.services;

import com.beowulfe.hap.accessories.ColorfulLightbulb;
import com.beowulfe.hap.accessories.DimmableLightbulb;
import com.beowulfe.hap.accessories.Lightbulb;
import com.beowulfe.hap.impl.characteristics.common.Name;
import com.beowulfe.hap.impl.characteristics.common.PowerStateCharacteristic;
import com.beowulfe.hap.impl.characteristics.lightbulb.BrightnessCharacteristic;
import com.beowulfe.hap.impl.characteristics.lightbulb.HueCharacteristic;
import com.beowulfe.hap.impl.characteristics.lightbulb.SaturationCharacteristic;

public class LightbulbService extends AbstractServiceImpl {

	public LightbulbService(Lightbulb lightbulb) {
		super("00000043-0000-1000-8000-0026BB765291");
		addCharacteristic(new Name(lightbulb));
		addCharacteristic(new PowerStateCharacteristic(
				() -> lightbulb.getLightbulbPowerState(),
				v -> lightbulb.setLightbulbPowerState(v),
				c -> lightbulb.subscribeLightbulbPowerState(c),
				() -> lightbulb.unsubscribeLightbulbPowerState()
			));
		
		if (lightbulb instanceof DimmableLightbulb) {
			addCharacteristic(new BrightnessCharacteristic((DimmableLightbulb) lightbulb));
		}
		
		if (lightbulb instanceof ColorfulLightbulb) {
			addCharacteristic(new HueCharacteristic((ColorfulLightbulb) lightbulb));
			addCharacteristic(new SaturationCharacteristic((ColorfulLightbulb) lightbulb));
		}
	}

}
