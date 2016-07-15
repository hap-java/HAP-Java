package com.beowulfe.hap.impl.services;

import com.beowulfe.hap.accessories.GarageDoor;
import com.beowulfe.hap.impl.characteristics.common.ObstructionDetectedCharacteristic;
import com.beowulfe.hap.impl.characteristics.garage.CurrentDoorStateCharacteristic;
import com.beowulfe.hap.impl.characteristics.garage.TargetDoorStateCharacteristic;

public class GarageDoorService extends AbstractServiceImpl {

	public GarageDoorService(GarageDoor door) {
		super("00000041-0000-1000-8000-0026BB765291", door);
		addCharacteristic(new CurrentDoorStateCharacteristic(door));
		addCharacteristic(new TargetDoorStateCharacteristic(door));
		addCharacteristic(new ObstructionDetectedCharacteristic(() -> door.getObstructionDetected(),
				c -> door.subscribeObstructionDetected(c),
				() -> door.unsubscribeObstructionDetected()));
	}

}