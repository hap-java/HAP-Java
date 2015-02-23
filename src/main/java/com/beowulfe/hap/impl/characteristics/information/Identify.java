package com.beowulfe.hap.impl.characteristics.information;

import com.beowulfe.hap.HomekitAccessory;
import com.beowulfe.hap.characteristics.WriteOnlyBooleanCharacteristic;

public class Identify extends WriteOnlyBooleanCharacteristic {

	private HomekitAccessory accessory;
	
	public Identify(HomekitAccessory accessory) throws Exception {
		super("00000014-0000-1000-8000-0026BB765291",
				"Identifies the accessory via a physical action on the accessory");
		this.accessory = accessory;
	}

	@Override
	public void setValue(Boolean value) throws Exception {
		if (value) {
			accessory.identify();
		}
	}

}
