package io.github.hapjava.services.impl;

import io.github.hapjava.accessories.TelevisionAccessory;
import io.github.hapjava.accessories.optionalcharacteristic.AccessoryWithBrightness;
import io.github.hapjava.accessories.optionalcharacteristic.AccessoryWithClosedCaptions;
import io.github.hapjava.accessories.optionalcharacteristic.AccessoryWithCurrentMediaState;
import io.github.hapjava.accessories.optionalcharacteristic.AccessoryWithName;
import io.github.hapjava.accessories.optionalcharacteristic.AccessoryWithPictureMode;
import io.github.hapjava.accessories.optionalcharacteristic.AccessoryWithPowerMode;
import io.github.hapjava.accessories.optionalcharacteristic.AccessoryWithTargetMediaState;
import io.github.hapjava.characteristics.impl.common.ActiveCharacteristic;
import io.github.hapjava.characteristics.impl.common.ActiveEnum;
import io.github.hapjava.characteristics.impl.common.ActiveIdentifierCharacteristic;
import io.github.hapjava.characteristics.impl.common.ConfiguredNameCharacteristic;
import io.github.hapjava.characteristics.impl.common.NameCharacteristic;
import io.github.hapjava.characteristics.impl.lightbulb.BrightnessCharacteristic;
import io.github.hapjava.characteristics.impl.television.ClosedCaptionsCharacteristic;
import io.github.hapjava.characteristics.impl.television.CurrentMediaStateCharacteristic;
import io.github.hapjava.characteristics.impl.television.PictureModeCharacteristic;
import io.github.hapjava.characteristics.impl.television.PowerModeCharacteristic;
import io.github.hapjava.characteristics.impl.television.RemoteKeyCharacteristic;
import io.github.hapjava.characteristics.impl.television.SleepDiscoveryModeCharacteristic;
import io.github.hapjava.characteristics.impl.television.TargetMediaStateCharacteristic;

/** This service describes a television. */
public class TelevisionService extends AbstractServiceImpl {

  public TelevisionService(
      ActiveCharacteristic activeCharacteristic,
      ActiveIdentifierCharacteristic activeIdentifierCharacteristic,
      ConfiguredNameCharacteristic configuredNameCharacteristic,
      RemoteKeyCharacteristic remoteKeyCharacteristic,
      SleepDiscoveryModeCharacteristic sleepDiscoveryModeCharacteristic) {
    super("000000D8-0000-1000-8000-0026BB765291");
    addCharacteristic(activeCharacteristic);
    addCharacteristic(activeIdentifierCharacteristic);
    addCharacteristic(configuredNameCharacteristic);
    addCharacteristic(remoteKeyCharacteristic);
    addCharacteristic(sleepDiscoveryModeCharacteristic);
  }

  public TelevisionService(TelevisionAccessory accessory) {
    this(
        new ActiveCharacteristic(
            () -> accessory.isActive().thenApply(s -> s ? ActiveEnum.ACTIVE : ActiveEnum.INACTIVE),
            (v) -> accessory.setActive(v == ActiveEnum.ACTIVE),
            accessory::subscribeActive,
            accessory::unsubscribeActive),
        new ActiveIdentifierCharacteristic(
            accessory::getActiveIdentifier,
            accessory::setActiveIdentifier,
            accessory::subscribeActiveIdentifier,
            accessory::unsubscribeActiveIdentifier),
        new ConfiguredNameCharacteristic(
            accessory::getConfiguredName,
            accessory::setConfiguredName,
            accessory::subscribeConfiguredName,
            accessory::unsubscribeConfiguredName),
        new RemoteKeyCharacteristic(accessory::setRemoteKey),
        new SleepDiscoveryModeCharacteristic(
            accessory::getSleepDiscoveryMode,
            accessory::subscribeSleepDiscoveryMode,
            accessory::unsubscribeSleepDiscoveryMode));
    if (accessory instanceof AccessoryWithName) {
      addOptionalCharacteristic(new NameCharacteristic(((AccessoryWithName) accessory)::getName));
    }
    if (accessory instanceof AccessoryWithBrightness) {
      addOptionalCharacteristic(
          new BrightnessCharacteristic(
              ((AccessoryWithBrightness) accessory)::getBrightness,
              ((AccessoryWithBrightness) accessory)::setBrightness,
              ((AccessoryWithBrightness) accessory)::subscribeBrightness,
              ((AccessoryWithBrightness) accessory)::unsubscribeBrightness));
    }
    if (accessory instanceof AccessoryWithPowerMode) {
      addOptionalCharacteristic(
          new PowerModeCharacteristic(((AccessoryWithPowerMode) accessory)::setPowerMode));
    }
    if (accessory instanceof AccessoryWithClosedCaptions) {
      addOptionalCharacteristic(
          new ClosedCaptionsCharacteristic(
              ((AccessoryWithClosedCaptions) accessory)::getClosedCaptions,
              ((AccessoryWithClosedCaptions) accessory)::setClosedCaptions,
              ((AccessoryWithClosedCaptions) accessory)::subscribeClosedCaptions,
              ((AccessoryWithClosedCaptions) accessory)::unsubscribeClosedCaptions));
    }
    if (accessory instanceof AccessoryWithCurrentMediaState) {
      addOptionalCharacteristic(
          new CurrentMediaStateCharacteristic(
              ((AccessoryWithCurrentMediaState) accessory)::getCurrentMediaState,
              ((AccessoryWithCurrentMediaState) accessory)::subscribeCurrentMediaState,
              ((AccessoryWithCurrentMediaState) accessory)::unsubscribeCurrentMediaState));
    }
    if (accessory instanceof AccessoryWithTargetMediaState) {
      addOptionalCharacteristic(
          new TargetMediaStateCharacteristic(
              ((AccessoryWithTargetMediaState) accessory)::getTargetMediaState,
              ((AccessoryWithTargetMediaState) accessory)::setTargetMediaState,
              ((AccessoryWithTargetMediaState) accessory)::subscribeTargetMediaState,
              ((AccessoryWithTargetMediaState) accessory)::unsubscribeTargetMediaState));
    }
    if (accessory instanceof AccessoryWithPictureMode) {
      addOptionalCharacteristic(
          new PictureModeCharacteristic(
              ((AccessoryWithPictureMode) accessory)::getPictureMode,
              ((AccessoryWithPictureMode) accessory)::setPictureMode,
              ((AccessoryWithPictureMode) accessory)::subscribePictureMode,
              ((AccessoryWithPictureMode) accessory)::unsubscribePictureMode));
    }
  }

  public void addOptionalCharacteristic(NameCharacteristic name) {
    addCharacteristic(name);
  }

  public void addOptionalCharacteristic(PowerModeCharacteristic powerMode) {
    addCharacteristic(powerMode);
  }

  public void addOptionalCharacteristic(ClosedCaptionsCharacteristic closedCaptions) {
    addCharacteristic(closedCaptions);
  }

  public void addOptionalCharacteristic(CurrentMediaStateCharacteristic currentMediaState) {
    addCharacteristic(currentMediaState);
  }

  public void addOptionalCharacteristic(TargetMediaStateCharacteristic targetMediaState) {
    addCharacteristic(targetMediaState);
  }

  public void addOptionalCharacteristic(PictureModeCharacteristic pictureMode) {
    addCharacteristic(pictureMode);
  }

  public void addOptionalCharacteristic(BrightnessCharacteristic brightness) {
    addCharacteristic(brightness);
  }
}
