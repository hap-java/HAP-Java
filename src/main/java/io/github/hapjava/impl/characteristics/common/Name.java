package io.github.hapjava.impl.characteristics.common;

import io.github.hapjava.characteristics.StaticStringCharacteristic;

public class Name extends StaticStringCharacteristic {

  public Name(String label) {
    super("00000023-0000-1000-8000-0026BB765291", "Name of the accessory", label);
  }
}
