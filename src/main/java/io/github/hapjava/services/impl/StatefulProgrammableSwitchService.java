package io.github.hapjava.services.impl;

import io.github.hapjava.accessories.StatefulProgrammableSwitchAccessory;
import io.github.hapjava.accessories.optionalcharacteristic.AccessoryWithName;
import io.github.hapjava.characteristics.impl.common.NameCharacteristic;
import io.github.hapjava.characteristics.impl.common.ProgrammableSwitchEventCharacteristic;
import io.github.hapjava.characteristics.impl.common.ProgrammableSwitchOutputStateCharacteristic;

/**
 * This service describes a stateful programmable switch. It is not part of the public HAP
 * specification; the definition follows HAP-NodeJS.
 */
public class StatefulProgrammableSwitchService extends AbstractServiceImpl {

  public StatefulProgrammableSwitchService(
      ProgrammableSwitchEventCharacteristic switchEvent,
      ProgrammableSwitchOutputStateCharacteristic outputState) {
    super("00000088-0000-1000-8000-0026BB765291");
    addCharacteristic(switchEvent);
    addCharacteristic(outputState);
  }

  public StatefulProgrammableSwitchService(StatefulProgrammableSwitchAccessory accessory) {
    this(
        new ProgrammableSwitchEventCharacteristic(
            accessory::getSwitchEvent,
            accessory::subscribeSwitchEvent,
            accessory::unsubscribeSwitchEvent),
        new ProgrammableSwitchOutputStateCharacteristic(
            accessory::getSwitchOutputState,
            accessory::setSwitchOutputState,
            accessory::subscribeSwitchOutputState,
            accessory::unsubscribeSwitchOutputState));
    if (accessory instanceof AccessoryWithName) {
      addOptionalCharacteristic(new NameCharacteristic(((AccessoryWithName) accessory)::getName));
    }
  }

  public void addOptionalCharacteristic(NameCharacteristic name) {
    addCharacteristic(name);
  }
}
