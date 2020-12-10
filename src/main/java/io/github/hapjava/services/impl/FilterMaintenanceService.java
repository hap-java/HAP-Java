package io.github.hapjava.services.impl;

import io.github.hapjava.accessories.FilterMaintenanceAccessory;
import io.github.hapjava.accessories.optionalcharacteristic.AccessoryWithFilterLifeLevel;
import io.github.hapjava.accessories.optionalcharacteristic.AccessoryWithName;
import io.github.hapjava.accessories.optionalcharacteristic.AccessoryWithResetFilterIndication;
import io.github.hapjava.characteristics.impl.common.NameCharacteristic;
import io.github.hapjava.characteristics.impl.filtermaintenance.FilterChangeIndicationCharacteristic;
import io.github.hapjava.characteristics.impl.filtermaintenance.FilterLifeLevelCharacteristic;
import io.github.hapjava.characteristics.impl.filtermaintenance.ResetFilterIndicationCharacteristic;

/** This service describes a filter maintenance. */
public class FilterMaintenanceService extends AbstractServiceImpl {

  public FilterMaintenanceService(FilterChangeIndicationCharacteristic filerChange) {
    super("000000BA-0000-1000-8000-0026BB765291");
    addCharacteristic(filerChange);
  }

  public FilterMaintenanceService(FilterMaintenanceAccessory accessory) {
    this(
        new FilterChangeIndicationCharacteristic(
            accessory::getFilterChangeIndication,
            accessory::subscribeFilterChangeIndication,
            accessory::unsubscribeFilterChangeIndication));

    if (accessory instanceof AccessoryWithName) {
      addOptionalCharacteristic(new NameCharacteristic(((AccessoryWithName) accessory)::getName));
    }

    if (accessory instanceof AccessoryWithFilterLifeLevel) {
      addOptionalCharacteristic(
          new FilterLifeLevelCharacteristic(
              ((AccessoryWithFilterLifeLevel) accessory)::getFilterLifeLevel,
              ((AccessoryWithFilterLifeLevel) accessory)::subscribeFilterLifeLevel,
              ((AccessoryWithFilterLifeLevel) accessory)::unsubscribeFilterLifeLevel));
    }

    if (accessory instanceof AccessoryWithResetFilterIndication) {
      addOptionalCharacteristic(
          new ResetFilterIndicationCharacteristic(
              ((AccessoryWithResetFilterIndication) accessory)::resetFilterIndication));
    }
  }

  public void addOptionalCharacteristic(NameCharacteristic name) {
    addCharacteristic(name);
  }

  public void addOptionalCharacteristic(FilterLifeLevelCharacteristic filterLifeLevel) {
    addCharacteristic(filterLifeLevel);
  }

  public void addOptionalCharacteristic(ResetFilterIndicationCharacteristic resetFilterIndication) {
    addCharacteristic(resetFilterIndication);
  }
}
