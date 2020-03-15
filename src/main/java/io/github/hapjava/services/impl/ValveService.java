package io.github.hapjava.services.impl;

import io.github.hapjava.accessories.ValveAccessory;
import io.github.hapjava.characteristics.impl.accessoryinformation.NameCharacteristic;
import io.github.hapjava.characteristics.impl.common.ActiveCharacteristic;
import io.github.hapjava.characteristics.impl.common.InUseCharacteristic;
import io.github.hapjava.characteristics.impl.common.IsConfiguredCharacteristic;
import io.github.hapjava.characteristics.impl.common.ServiceLabelIndexCharacteristic;
import io.github.hapjava.characteristics.impl.common.StatusFaultCharacteristic;
import io.github.hapjava.characteristics.impl.valve.SetDurationCharacteristic;
import io.github.hapjava.characteristics.impl.valve.ValveTypeCharacteristic;

/**
 * This service describes accessories like irrigation valves or water outlets. A valve is set to In
 * Use when there are fluid flowing through the valve.
 */
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

  public ValveService(ValveAccessory valve) {
    this(
        new ActiveCharacteristic(
            valve::getValveActive,
            valve::setValveActive,
            valve::subscribeValveActive,
            valve::unsubscribeValveActive),
        new InUseCharacteristic(
            valve::getValveInUse, valve::subscribeValveInUse, valve::unsubscribeValveInUse),
        new ValveTypeCharacteristic(
            valve::getValveType, valve::subscribeValveType, valve::unsubscribeValveType));
  }

  public void addOptionalCharacteristic(NameCharacteristic name) {
    addCharacteristic(name);
  }

  public void addOptionalCharacteristic(SetDurationCharacteristic setDurationCharacteristic) {
    addCharacteristic(setDurationCharacteristic);
  }

  public void addOptionalCharacteristic(IsConfiguredCharacteristic isConfiguredCharacteristic) {
    addCharacteristic(isConfiguredCharacteristic);
  }

  public void addOptionalCharacteristic(
      ServiceLabelIndexCharacteristic serviceLabelIndexCharacteristic) {
    addCharacteristic(serviceLabelIndexCharacteristic);
  }

  public void addOptionalCharacteristic(StatusFaultCharacteristic statusFaultCharacteristic) {
    addCharacteristic(statusFaultCharacteristic);
  }
}
