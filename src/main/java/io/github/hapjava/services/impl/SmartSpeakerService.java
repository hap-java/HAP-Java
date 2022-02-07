package io.github.hapjava.services.impl;

import io.github.hapjava.accessories.SmartSpeakerAccessory;
import io.github.hapjava.accessories.optionalcharacteristic.AccessoryWithAirPlayEnable;
import io.github.hapjava.accessories.optionalcharacteristic.AccessoryWithConfiguredName;
import io.github.hapjava.accessories.optionalcharacteristic.AccessoryWithMute;
import io.github.hapjava.accessories.optionalcharacteristic.AccessoryWithName;
import io.github.hapjava.accessories.optionalcharacteristic.AccessoryWithVolume;
import io.github.hapjava.characteristics.impl.audio.MuteCharacteristic;
import io.github.hapjava.characteristics.impl.audio.VolumeCharacteristic;
import io.github.hapjava.characteristics.impl.common.AirPlayEnableCharacteristic;
import io.github.hapjava.characteristics.impl.common.ConfiguredNameCharacteristic;
import io.github.hapjava.characteristics.impl.common.NameCharacteristic;
import io.github.hapjava.characteristics.impl.television.CurrentMediaStateCharacteristic;
import io.github.hapjava.characteristics.impl.television.TargetMediaStateCharacteristic;

/** A smart speaker service can be used to control the audio output settings on a speaker device. */
public class SmartSpeakerService extends AbstractServiceImpl {

  public SmartSpeakerService(
      CurrentMediaStateCharacteristic currentMediaStateCharacteristic,
      TargetMediaStateCharacteristic targetMediaStateCharacteristic) {
    super("00000228-0000-1000-8000-0026BB765291");
    addCharacteristic(currentMediaStateCharacteristic);
    addCharacteristic(targetMediaStateCharacteristic);
  }

  public SmartSpeakerService(SmartSpeakerAccessory accessory) {
    this(
        new CurrentMediaStateCharacteristic(
            accessory::getCurrentMediaState,
            accessory::subscribeCurrentMediaState,
            accessory::unsubscribeCurrentMediaState),
        new TargetMediaStateCharacteristic(
            accessory::getTargetMediaState,
            accessory::setTargetMediaState,
            accessory::subscribeTargetMediaState,
            accessory::unsubscribeTargetMediaState));
    if (accessory instanceof AccessoryWithName) {
      addOptionalCharacteristic(new NameCharacteristic(((AccessoryWithName) accessory)::getName));
    }
    if (accessory instanceof AccessoryWithVolume) {
      addOptionalCharacteristic(
          new VolumeCharacteristic(
              ((AccessoryWithVolume) accessory)::getVolume,
              ((AccessoryWithVolume) accessory)::setVolume,
              ((AccessoryWithVolume) accessory)::subscribeVolume,
              ((AccessoryWithVolume) accessory)::unsubscribeVolume));
    }
    if (accessory instanceof AccessoryWithConfiguredName) {
      addOptionalCharacteristic(
          new ConfiguredNameCharacteristic(
              ((AccessoryWithConfiguredName) accessory)::getConfiguredName,
              ((AccessoryWithConfiguredName) accessory)::setConfiguredName,
              ((AccessoryWithConfiguredName) accessory)::subscribeConfiguredName,
              ((AccessoryWithConfiguredName) accessory)::unsubscribeConfiguredName));
    }
    if (accessory instanceof AccessoryWithMute) {
      addOptionalCharacteristic(
          new MuteCharacteristic(
              ((AccessoryWithMute) accessory)::isMuted,
              ((AccessoryWithMute) accessory)::setMute,
              ((AccessoryWithMute) accessory)::subscribeMuteState,
              ((AccessoryWithMute) accessory)::unsubscribeMuteState));
    }
    if (accessory instanceof AccessoryWithAirPlayEnable) {
      addOptionalCharacteristic(
          new AirPlayEnableCharacteristic(
              ((AccessoryWithAirPlayEnable) accessory)::getAirPlayEnable,
              ((AccessoryWithAirPlayEnable) accessory)::setAirPlayEnable,
              ((AccessoryWithAirPlayEnable) accessory)::subscribeAirPlayEnable,
              ((AccessoryWithAirPlayEnable) accessory)::unsubscribeAirPlayEnable));
    }
  }

  public void addOptionalCharacteristic(NameCharacteristic name) {
    addCharacteristic(name);
  }

  public void addOptionalCharacteristic(VolumeCharacteristic volume) {
    addCharacteristic(volume);
  }

  public void addOptionalCharacteristic(ConfiguredNameCharacteristic configuredNameCharacteristic) {
    addCharacteristic(configuredNameCharacteristic);
  }

  public void addOptionalCharacteristic(MuteCharacteristic muteCharacteristic) {
    addCharacteristic(muteCharacteristic);
  }

  public void addOptionalCharacteristic(AirPlayEnableCharacteristic airPlayEnableCharacteristic) {
    addCharacteristic(airPlayEnableCharacteristic);
  }
}
