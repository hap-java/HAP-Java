package io.github.hapjava.services.impl;

import io.github.hapjava.accessories.SwitchAccessory;
import io.github.hapjava.characteristics.impl.accessoryinformation.NameCharacteristic;
import io.github.hapjava.characteristics.impl.common.OnCharacteristic;

/** This service describes a binary switch. */
public class SwitchService extends AbstractServiceImpl {

  public SwitchService(OnCharacteristic onState) {
    super("00000049-0000-1000-8000-0026BB765291");
    addCharacteristic(onState);
  }

  public SwitchService(SwitchAccessory accessory) {
    this(
        new OnCharacteristic(
            accessory::getSwitchState,
            accessory::setSwitchState,
            accessory::subscribeSwitchState,
            accessory::unsubscribeSwitchState));
  }

  public void addOptionalCharacteristic(NameCharacteristic name) {
    addCharacteristic(name);
  }
}
