package io.github.hapjava.services.impl;

import io.github.hapjava.accessories.FaucetAccessory;
import io.github.hapjava.accessories.optionalcharacteristic.AccessoryWithName;
import io.github.hapjava.accessories.optionalcharacteristic.AccessoryWithStatusFault;
import io.github.hapjava.characteristics.impl.common.ActiveCharacteristic;
import io.github.hapjava.characteristics.impl.common.ActiveEnum;
import io.github.hapjava.characteristics.impl.common.NameCharacteristic;
import io.github.hapjava.characteristics.impl.common.StatusFaultCharacteristic;

/** This service describes accessories like faucets or shower heads. This service must only be included when an accessory
 has either a linked {@link HeaterCoolerService} ith single linked {@link ValveService}  or multiple
 linked {@link ValveService}  (with/without {@link HeaterCoolerService} to describe water
 outlets. This service serves as a top level service for such accessories. */

public class FaucetService extends AbstractServiceImpl {

  public FaucetService(ActiveCharacteristic active) {
    super("000000D7-0000-1000-8000-0026BB765291");
    addCharacteristic(active);
  }

  public FaucetService(FaucetAccessory accessory) {
    this(
        new ActiveCharacteristic(
            () -> accessory.isActive().thenApply(s -> s ? ActiveEnum.ACTIVE : ActiveEnum.INACTIVE),
            (v) -> accessory.setActive(v == ActiveEnum.ACTIVE),
            accessory::subscribeActive,
            accessory::unsubscribeActive));
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
  }

  public void addOptionalCharacteristic(NameCharacteristic name) {
    addCharacteristic(name);
  }
  public void addOptionalCharacteristic(StatusFaultCharacteristic statusFault) {
    addCharacteristic(statusFault);
  }

}
