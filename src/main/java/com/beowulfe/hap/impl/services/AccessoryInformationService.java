package com.beowulfe.hap.impl.services;

import com.beowulfe.hap.HomekitAccessory;
import com.beowulfe.hap.impl.characteristics.common.Name;
import com.beowulfe.hap.impl.characteristics.information.Identify;
import com.beowulfe.hap.impl.characteristics.information.Manufacturer;
import com.beowulfe.hap.impl.characteristics.information.Model;
import com.beowulfe.hap.impl.characteristics.information.SerialNumber;

public class AccessoryInformationService extends AbstractServiceImpl {

	public AccessoryInformationService(HomekitAccessory accessory) throws Exception {
		super("0000003E-0000-1000-8000-0026BB765291");
		addCharacteristic(new Name(accessory));
		addCharacteristic(new Manufacturer(accessory));
		addCharacteristic(new Model(accessory));
		addCharacteristic(new SerialNumber(accessory));
		addCharacteristic(new Identify(accessory));
	}

}
