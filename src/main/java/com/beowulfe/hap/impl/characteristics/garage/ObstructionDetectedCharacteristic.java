package com.beowulfe.hap.impl.characteristics.garage;

import java.util.concurrent.CompletableFuture;

import com.beowulfe.hap.HomekitCharacteristicChangeCallback;
import com.beowulfe.hap.accessories.GarageDoor;
import com.beowulfe.hap.characteristics.BooleanCharacteristic;
import com.beowulfe.hap.characteristics.EventableCharacteristic;

public class ObstructionDetectedCharacteristic extends BooleanCharacteristic implements EventableCharacteristic {

	private final GarageDoor door;
	
	public ObstructionDetectedCharacteristic(GarageDoor door) {
		super("00000024-0000-1000-8000-0026BB765291", false, true, "An obstruction to the door has been detected");
		this.door = door;
	}

	@Override
	protected void setValue(Boolean value) throws Exception {
		//Read Only
	}

	@Override
	protected CompletableFuture<Boolean> getValue() {
		return door.getObstructionDetected();
	}

	@Override
	public void subscribe(HomekitCharacteristicChangeCallback callback) {
		door.subscribeObstructionDetected(callback);
	}

	@Override
	public void unsubscribe() {
		door.unsubscribeObstructionDetected();
	}

	

}
