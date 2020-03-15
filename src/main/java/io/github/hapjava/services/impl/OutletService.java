package io.github.hapjava.services.impl;

import io.github.hapjava.accessories.OutletAccessory;
import io.github.hapjava.characteristics.impl.accessoryinformation.NameCharacteristic;
import io.github.hapjava.characteristics.impl.common.OnCharacteristic;
import io.github.hapjava.characteristics.impl.outlet.OutletInUseCharacteristic;

/** This service describes a power outlet. */
public class OutletService extends AbstractServiceImpl {

  public OutletService(OnCharacteristic onState, OutletInUseCharacteristic outletInUse) {
    super("00000047-0000-1000-8000-0026BB765291");
    addCharacteristic(onState);
    addCharacteristic(outletInUse);
  }

  public OutletService(OutletAccessory outlet) {
    this(
        new OnCharacteristic(
            outlet::getPowerState,
            outlet::setPowerState,
            outlet::subscribePowerState,
            outlet::unsubscribePowerState),
        new OutletInUseCharacteristic(
            outlet::getOutletInUse, outlet::subscribeOutletInUse, outlet::unsubscribeOutletInUse));
  }

  public void addOptionalCharacteristic(NameCharacteristic name) {
    addCharacteristic(name);
  }
}
