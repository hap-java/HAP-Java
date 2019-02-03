package com.beowulfe.hap.impl.characteristics.common;

import com.beowulfe.hap.characteristics.StaticStringCharacteristic;

public class Name extends StaticStringCharacteristic {

  public Name(String label) {
    super("00000023-0000-1000-8000-0026BB765291", "Name of the accessory", label);
  }
}
