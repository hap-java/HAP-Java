package io.github.hapjava.services.impl;

import io.github.hapjava.accessories.InputSourceAccessory;
import io.github.hapjava.accessories.optionalcharacteristic.AccessoryWithIdentifier;
import io.github.hapjava.accessories.optionalcharacteristic.AccessoryWithInputDeviceType;
import io.github.hapjava.accessories.optionalcharacteristic.AccessoryWithName;
import io.github.hapjava.accessories.optionalcharacteristic.AccessoryWithTargetVisibilityState;
import io.github.hapjava.characteristics.impl.common.ConfiguredNameCharacteristic;
import io.github.hapjava.characteristics.impl.common.IdentifierCharacteristic;
import io.github.hapjava.characteristics.impl.common.IsConfiguredCharacteristic;
import io.github.hapjava.characteristics.impl.common.NameCharacteristic;
import io.github.hapjava.characteristics.impl.inputsource.CurrentVisibilityStateCharacteristic;
import io.github.hapjava.characteristics.impl.inputsource.InputDeviceTypeCharacteristic;
import io.github.hapjava.characteristics.impl.inputsource.InputSourceTypeCharacteristic;
import io.github.hapjava.characteristics.impl.inputsource.TargetVisibilityStateCharacteristic;

/** This service describes a input source. */
public class InputSourceService extends AbstractServiceImpl {

  public InputSourceService(
      ConfiguredNameCharacteristic configuredNameCharacteristic,
      InputSourceTypeCharacteristic inputSourceTypeCharacteristic,
      IsConfiguredCharacteristic isConfiguredCharacteristic,
      CurrentVisibilityStateCharacteristic currentVisibilityStateCharacteristic) {
    super("000000D9-0000-1000-8000-0026BB765291");
    addCharacteristic(configuredNameCharacteristic);
    addCharacteristic(inputSourceTypeCharacteristic);
    addCharacteristic(isConfiguredCharacteristic);
    addCharacteristic(currentVisibilityStateCharacteristic);
  }

  public InputSourceService(InputSourceAccessory accessory) {
    this(
        new ConfiguredNameCharacteristic(
            accessory::getConfiguredName,
            accessory::setConfiguredName,
            accessory::subscribeConfiguredName,
            accessory::unsubscribeConfiguredName),
        new InputSourceTypeCharacteristic(
            accessory::getInputSourceType,
            accessory::subscribeInputSourceType,
            accessory::unsubscribeInputSourceType),
        new IsConfiguredCharacteristic(
            accessory::isConfigured,
            accessory::setIsConfigured,
            accessory::subscribeIsConfigured,
            accessory::unsubscribeIsConfigured),
        new CurrentVisibilityStateCharacteristic(
            accessory::getCurrentVisibilityState,
            accessory::subscribeCurrentVisibilityState,
            accessory::unsubscribeCurrentVisibilityState));
    if (accessory instanceof AccessoryWithName) {
      addOptionalCharacteristic(new NameCharacteristic(((AccessoryWithName) accessory)::getName));
    }
    if (accessory instanceof AccessoryWithIdentifier) {
      addOptionalCharacteristic(
          new IdentifierCharacteristic(((AccessoryWithIdentifier) accessory)::getIdentifier));
    }
    if (accessory instanceof AccessoryWithInputDeviceType) {
      addOptionalCharacteristic(
          new InputDeviceTypeCharacteristic(
              ((AccessoryWithInputDeviceType) accessory)::getInputDeviceType,
              ((AccessoryWithInputDeviceType) accessory)::subscribeInputDeviceType,
              ((AccessoryWithInputDeviceType) accessory)::unsubscribeInputDeviceType));
    }
    if (accessory instanceof AccessoryWithTargetVisibilityState) {
      addOptionalCharacteristic(
          new TargetVisibilityStateCharacteristic(
              ((AccessoryWithTargetVisibilityState) accessory)::getTargetVisibilityState,
              ((AccessoryWithTargetVisibilityState) accessory)::setTargetVisibilityState,
              ((AccessoryWithTargetVisibilityState) accessory)::subscribeTargetVisibilityState,
              ((AccessoryWithTargetVisibilityState) accessory)::unsubscribeTargetVisibilityState));
    }
  }

  public void addOptionalCharacteristic(NameCharacteristic name) {
    addCharacteristic(name);
  }

  public void addOptionalCharacteristic(IdentifierCharacteristic identifier) {
    addCharacteristic(identifier);
  }

  public void addOptionalCharacteristic(InputDeviceTypeCharacteristic inputDeviceType) {
    addCharacteristic(inputDeviceType);
  }

  public void addOptionalCharacteristic(TargetVisibilityStateCharacteristic targetVisibilityState) {
    addCharacteristic(targetVisibilityState);
  }
}
