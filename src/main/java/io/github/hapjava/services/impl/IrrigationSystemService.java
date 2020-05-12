package io.github.hapjava.services.impl;

import io.github.hapjava.accessories.IrrigationSystemAccessory;
import io.github.hapjava.accessories.optionalcharacteristic.AccessoryWithName;
import io.github.hapjava.accessories.optionalcharacteristic.AccessoryWithRemainingDuration;
import io.github.hapjava.accessories.optionalcharacteristic.AccessoryWithStatusFault;
import io.github.hapjava.characteristics.impl.common.ActiveCharacteristic;
import io.github.hapjava.characteristics.impl.common.InUseCharacteristic;
import io.github.hapjava.characteristics.impl.common.NameCharacteristic;
import io.github.hapjava.characteristics.impl.common.ProgramModeCharacteristic;
import io.github.hapjava.characteristics.impl.common.StatusFaultCharacteristic;
import io.github.hapjava.characteristics.impl.valve.RemainingDurationCharacteristic;

/** An irrigation system service */
public class IrrigationSystemService extends AbstractServiceImpl {

  public IrrigationSystemService(
      ActiveCharacteristic activeCharacteristic,
      InUseCharacteristic inUseCharacteristic,
      ProgramModeCharacteristic programModeCharacteristic) {
    super("000000CF-0000-1000-8000-0026BB765291");
    addCharacteristic(activeCharacteristic);
    addCharacteristic(inUseCharacteristic);
    addCharacteristic(programModeCharacteristic);
  }

  public IrrigationSystemService(IrrigationSystemAccessory accessory) {
    this(
        new ActiveCharacteristic(
            accessory::getActive,
            accessory::setActive,
            accessory::subscribeActive,
            accessory::unsubscribeActive),
        new InUseCharacteristic(
            accessory::getInUse, accessory::subscribeInUse, accessory::unsubscribeInUse),
        new ProgramModeCharacteristic(
            accessory::getProgramMode,
            accessory::subscribeProgramMode,
            accessory::unsubscribeProgramMode));
    if (accessory instanceof AccessoryWithName) {
      addOptionalCharacteristic(new NameCharacteristic(((AccessoryWithName) accessory)::getName));
    }
    if (accessory instanceof AccessoryWithStatusFault) {
      addOptionalCharacteristic(
          new StatusFaultCharacteristic(
              ((AccessoryWithStatusFault) accessory)::getStatusFault,
              ((AccessoryWithStatusFault) accessory)::subscribeStatusFault,
              ((AccessoryWithStatusFault) accessory)::unsubscribeStatusFault));
    }
    if (accessory instanceof AccessoryWithRemainingDuration) {
      addOptionalCharacteristic(
          new RemainingDurationCharacteristic(
              ((AccessoryWithRemainingDuration) accessory)::getRemainingDuration,
              ((AccessoryWithRemainingDuration) accessory)::subscribeRemainingDuration,
              ((AccessoryWithRemainingDuration) accessory)::unsubscribeRemainingDuration));
    }
  }

  public void addOptionalCharacteristic(NameCharacteristic name) {
    addCharacteristic(name);
  }

  public void addOptionalCharacteristic(StatusFaultCharacteristic statusFaultCharacteristic) {
    addCharacteristic(statusFaultCharacteristic);
  }

  public void addOptionalCharacteristic(
      RemainingDurationCharacteristic remainingDurationCharacteristic) {
    addCharacteristic(remainingDurationCharacteristic);
  }
}
