package io.github.hapjava.impl.services;

import io.github.hapjava.HomekitAccessory;
import io.github.hapjava.impl.characteristics.information.Identify;
import io.github.hapjava.impl.characteristics.information.Manufacturer;
import io.github.hapjava.impl.characteristics.information.Model;
import io.github.hapjava.impl.characteristics.information.SerialNumber;

public class AccessoryInformationService extends AbstractServiceImpl {

  public AccessoryInformationService(HomekitAccessory accessory) throws Exception {
    this(accessory, accessory.getLabel());
  }

  public AccessoryInformationService(HomekitAccessory accessory, String serviceName)
      throws Exception {
    super("0000003E-0000-1000-8000-0026BB765291", accessory, serviceName);
    addCharacteristic(new Manufacturer(accessory));
    addCharacteristic(new Model(accessory));
    addCharacteristic(new SerialNumber(accessory));
    addCharacteristic(new Identify(accessory));
  }
}
