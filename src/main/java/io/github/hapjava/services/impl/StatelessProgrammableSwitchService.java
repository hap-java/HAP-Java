package io.github.hapjava.services.impl;

import io.github.hapjava.accessories.StatelessProgrammableSwitchAccessory;
import io.github.hapjava.accessories.optionalcharacteristic.AccessoryWithName;
import io.github.hapjava.accessories.optionalcharacteristic.AccessoryWithServiceLabelIndex;
import io.github.hapjava.characteristics.impl.common.NameCharacteristic;
import io.github.hapjava.characteristics.impl.common.ProgrammableSwitchEventCharacteristic;
import io.github.hapjava.characteristics.impl.common.ServiceLabelIndexCharacteristic;

/**
 * This service describes a stateless programmable switch.
 *
 * @author Eugen Freiter
 */
public class StatelessProgrammableSwitchService extends AbstractServiceImpl {

  public StatelessProgrammableSwitchService(ProgrammableSwitchEventCharacteristic switchEvent) {
    super("00000089-0000-1000-8000-0026BB765291");
    addCharacteristic(switchEvent);
  }

  public StatelessProgrammableSwitchService(StatelessProgrammableSwitchAccessory accessory) {
    this(
        new ProgrammableSwitchEventCharacteristic(
            accessory::getSwitchEvent,
            accessory::subscribeSwitchEvent,
            accessory::unsubscribeSwitchEvent));
    if (accessory instanceof AccessoryWithName) {
      addOptionalCharacteristic(new NameCharacteristic(((AccessoryWithName) accessory)::getName));
    }
    if (accessory instanceof AccessoryWithServiceLabelIndex) {
      addOptionalCharacteristic(
          new ServiceLabelIndexCharacteristic(
              ((AccessoryWithServiceLabelIndex) accessory)::getServiceLabelIndex));
    }
  }

  public void addOptionalCharacteristic(NameCharacteristic name) {
    addCharacteristic(name);
  }

  public void addOptionalCharacteristic(
      ServiceLabelIndexCharacteristic serviceLabelIndexCharacteristic) {
    addCharacteristic(serviceLabelIndexCharacteristic);
  }
}
