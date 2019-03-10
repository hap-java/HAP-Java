package io.github.hapjava.impl.characteristics.information;

import io.github.hapjava.HomekitAccessory;
import io.github.hapjava.characteristics.StaticStringCharacteristic;

public class Model extends StaticStringCharacteristic {

  public Model(HomekitAccessory accessory) throws Exception {
    super("00000021-0000-1000-8000-0026BB765291", "The name of the model", accessory.getModel());
  }
}
