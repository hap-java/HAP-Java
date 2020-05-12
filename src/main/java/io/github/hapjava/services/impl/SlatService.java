package io.github.hapjava.services.impl;

import io.github.hapjava.accessories.SlatAccessory;
import io.github.hapjava.accessories.optionalcharacteristic.AccessoryWithCurrentTilting;
import io.github.hapjava.accessories.optionalcharacteristic.AccessoryWithName;
import io.github.hapjava.accessories.optionalcharacteristic.AccessoryWithSwingMode;
import io.github.hapjava.accessories.optionalcharacteristic.AccessoryWithTargetTilting;
import io.github.hapjava.characteristics.impl.common.NameCharacteristic;
import io.github.hapjava.characteristics.impl.fan.SwingModeCharacteristic;
import io.github.hapjava.characteristics.impl.slat.CurrentSlatStateCharacteristic;
import io.github.hapjava.characteristics.impl.slat.CurrentTiltAngleCharacteristic;
import io.github.hapjava.characteristics.impl.slat.SlatTypeCharacteristic;
import io.github.hapjava.characteristics.impl.slat.TargetTiltAngleCharacteristic;

/** This service describes a slat */
public class SlatService extends AbstractServiceImpl {

  public SlatService(
      CurrentSlatStateCharacteristic state, SlatTypeCharacteristic slatTypeCharacteristic) {
    super("000000B9-0000-1000-8000-0026BB765291");
    addCharacteristic(state);
    addCharacteristic(slatTypeCharacteristic);
  }

  public SlatService(SlatAccessory accessory) {
    this(
        new CurrentSlatStateCharacteristic(
            accessory::getSlatState,
            accessory::subscribeSlatState,
            accessory::unsubscribeSlatState),
        new SlatTypeCharacteristic(accessory::getSlatType));
    if (accessory instanceof AccessoryWithName) {
      addOptionalCharacteristic(new NameCharacteristic(((AccessoryWithName) accessory)::getName));
    }
    if (accessory instanceof AccessoryWithSwingMode) {
      addOptionalCharacteristic(
          new SwingModeCharacteristic(
              ((AccessoryWithSwingMode) accessory)::getSwingMode,
              ((AccessoryWithSwingMode) accessory)::setSwingMode,
              ((AccessoryWithSwingMode) accessory)::subscribeSwingMode,
              ((AccessoryWithSwingMode) accessory)::unsubscribeSwingMode));
    }
    if (accessory instanceof AccessoryWithCurrentTilting) {
      addOptionalCharacteristic(
          new CurrentTiltAngleCharacteristic(
              ((AccessoryWithCurrentTilting) accessory)::getCurrentTiltAngle,
              ((AccessoryWithCurrentTilting) accessory)::subscribeCurrentTiltAngle,
              ((AccessoryWithCurrentTilting) accessory)::unsubscribeCurrentTiltAngle));
    }
    if (accessory instanceof AccessoryWithTargetTilting) {
      addOptionalCharacteristic(
          new TargetTiltAngleCharacteristic(
              ((AccessoryWithTargetTilting) accessory)::getTargetTiltAngle,
              ((AccessoryWithTargetTilting) accessory)::setTargetTiltAngle,
              ((AccessoryWithTargetTilting) accessory)::subscribeTargetTiltAngle,
              ((AccessoryWithTargetTilting) accessory)::unsubscribeTargetTiltAngle));
    }
  }

  public void addOptionalCharacteristic(NameCharacteristic name) {
    addCharacteristic(name);
  }

  public void addOptionalCharacteristic(SwingModeCharacteristic mode) {
    addCharacteristic(mode);
  }

  public void addOptionalCharacteristic(
      TargetTiltAngleCharacteristic targetTiltAngleCharacteristic) {
    addCharacteristic(targetTiltAngleCharacteristic);
  }

  public void addOptionalCharacteristic(
      CurrentTiltAngleCharacteristic currentTiltAngleCharacteristic) {
    addCharacteristic(currentTiltAngleCharacteristic);
  }
}
