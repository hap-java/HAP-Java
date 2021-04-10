package io.github.hapjava.services.impl;

import io.github.hapjava.accessories.ValveAccessory;
import io.github.hapjava.accessories.optionalcharacteristic.AccessoryWithDuration;
import io.github.hapjava.accessories.optionalcharacteristic.AccessoryWithIsConfigured;
import io.github.hapjava.accessories.optionalcharacteristic.AccessoryWithName;
import io.github.hapjava.accessories.optionalcharacteristic.AccessoryWithRemainingDuration;
import io.github.hapjava.accessories.optionalcharacteristic.AccessoryWithServiceLabelIndex;
import io.github.hapjava.accessories.optionalcharacteristic.AccessoryWithStatusFault;
import io.github.hapjava.characteristics.impl.common.ActiveCharacteristic;
import io.github.hapjava.characteristics.impl.common.InUseCharacteristic;
import io.github.hapjava.characteristics.impl.common.IsConfiguredCharacteristic;
import io.github.hapjava.characteristics.impl.common.NameCharacteristic;
import io.github.hapjava.characteristics.impl.common.ServiceLabelIndexCharacteristic;
import io.github.hapjava.characteristics.impl.common.StatusFaultCharacteristic;
import io.github.hapjava.characteristics.impl.valve.RemainingDurationCharacteristic;
import io.github.hapjava.characteristics.impl.valve.SetDurationCharacteristic;
import io.github.hapjava.characteristics.impl.valve.ValveTypeCharacteristic;

/** This service describes accessories valves. */
public class ValveService extends AbstractServiceImpl {

  public ValveService(
      ActiveCharacteristic activeCharacteristic,
      InUseCharacteristic inUseCharacteristic,
      ValveTypeCharacteristic valveTypeCharacteristic) {
    super("000000D0-0000-1000-8000-0026BB765291");
    addCharacteristic(activeCharacteristic);
    addCharacteristic(inUseCharacteristic);
    addCharacteristic(valveTypeCharacteristic);
  }

  public ValveService(ValveAccessory accessory) {
    this(
        new ActiveCharacteristic(
            accessory::getValveActive,
            accessory::setValveActive,
            accessory::subscribeValveActive,
            accessory::unsubscribeValveActive),
        new InUseCharacteristic(
            accessory::getValveInUse,
            accessory::subscribeValveInUse,
            accessory::unsubscribeValveInUse),
        new ValveTypeCharacteristic(
            accessory::getValveType,
            accessory::subscribeValveType,
            accessory::unsubscribeValveType));
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
    if (accessory instanceof AccessoryWithDuration) {
      addOptionalCharacteristic(
          new SetDurationCharacteristic(
              ((AccessoryWithDuration) accessory).getMinDuration(),
              ((AccessoryWithDuration) accessory).getMaxDuration(),
              ((AccessoryWithDuration) accessory)::getSetDuration,
              ((AccessoryWithDuration) accessory)::setSetDuration,
              ((AccessoryWithDuration) accessory)::subscribeSetDuration,
              ((AccessoryWithDuration) accessory)::unsubscribeSetDuration));
    }
    if (accessory instanceof AccessoryWithRemainingDuration) {
      addOptionalCharacteristic(
          new RemainingDurationCharacteristic(
              ((AccessoryWithRemainingDuration) accessory).getMinRemainingDuration(),
              ((AccessoryWithRemainingDuration) accessory).getMaxRemainingDuration(),
              ((AccessoryWithRemainingDuration) accessory)::getRemainingDuration,
              ((AccessoryWithRemainingDuration) accessory)::subscribeRemainingDuration,
              ((AccessoryWithRemainingDuration) accessory)::unsubscribeRemainingDuration));
    }
    if (accessory instanceof AccessoryWithIsConfigured) {
      addOptionalCharacteristic(
          new IsConfiguredCharacteristic(
              ((AccessoryWithIsConfigured) accessory)::getIsConfigured,
              ((AccessoryWithIsConfigured) accessory)::setIsConfigured,
              ((AccessoryWithIsConfigured) accessory)::subscribeIsConfigured,
              ((AccessoryWithIsConfigured) accessory)::unsubscribeIsConfigured));
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

  public void addOptionalCharacteristic(StatusFaultCharacteristic statusFaultCharacteristic) {
    addCharacteristic(statusFaultCharacteristic);
  }

  public void addOptionalCharacteristic(SetDurationCharacteristic setDurationCharacteristic) {
    addCharacteristic(setDurationCharacteristic);
  }

  public void addOptionalCharacteristic(
      RemainingDurationCharacteristic remainingDurationCharacteristic) {
    addCharacteristic(remainingDurationCharacteristic);
  }

  public void addOptionalCharacteristic(IsConfiguredCharacteristic isConfiguredCharacteristic) {
    addCharacteristic(isConfiguredCharacteristic);
  }

  public void addOptionalCharacteristic(
      ServiceLabelIndexCharacteristic serviceLabelIndexCharacteristic) {
    addCharacteristic(serviceLabelIndexCharacteristic);
  }
}
