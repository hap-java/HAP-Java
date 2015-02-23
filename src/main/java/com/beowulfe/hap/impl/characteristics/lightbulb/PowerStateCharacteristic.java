package com.beowulfe.hap.impl.characteristics.lightbulb;

import java.util.concurrent.CompletableFuture;

import com.beowulfe.hap.HomekitCharacteristicChangeCallback;
import com.beowulfe.hap.accessories.Lightbulb;
import com.beowulfe.hap.characteristics.BooleanCharacteristic;
import com.beowulfe.hap.characteristics.EventableCharacteristic;

public class PowerStateCharacteristic extends BooleanCharacteristic implements EventableCharacteristic {

	private final Lightbulb lightbulb;
	
	public PowerStateCharacteristic(Lightbulb lightbulb) {
		super(	"00000025-0000-1000-8000-0026BB765291",
				true,
				true,
				"Turn on and off");
		this.lightbulb = lightbulb;
	}

	@Override
	public void setValue(Boolean value) throws Exception {
		lightbulb.setLightbulbPowerState(value).get();
	}

	@Override
	protected CompletableFuture<Boolean> getValue() {
		return lightbulb.getLightbulbPowerState();
	}

	@Override
	public void subscribe(HomekitCharacteristicChangeCallback callback) {
		lightbulb.subscribeLightbulbPowerState(callback);
	}
	
	@Override
	public void unsubscribe() {
		lightbulb.unsubscribeLightbulbPowerState();
	}
	
}
