package com.beowulfe.hap.impl.characteristics.information;

import com.beowulfe.hap.HomekitAccessory;
import com.beowulfe.hap.characteristics.StaticStringCharacteristic;

public class SerialNumber extends StaticStringCharacteristic {

  public SerialNumber(HomekitAccessory accessory) throws Exception {
    super(
        "00000030-0000-1000-8000-0026BB765291",
        "The serial number of the accessory",
        accessory.getSerialNumber());
  }
}
