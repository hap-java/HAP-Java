package io.github.hapjava.services.impl;

import io.github.hapjava.accessories.TelevisionSpeakerAccessory;
import io.github.hapjava.accessories.optionalcharacteristic.AccessoryWithActive;
import io.github.hapjava.accessories.optionalcharacteristic.AccessoryWithVolume;
import io.github.hapjava.accessories.optionalcharacteristic.AccessoryWithVolumeControlType;
import io.github.hapjava.accessories.optionalcharacteristic.AccessoryWithVolumeSelector;
import io.github.hapjava.characteristics.impl.audio.MuteCharacteristic;
import io.github.hapjava.characteristics.impl.audio.VolumeCharacteristic;
import io.github.hapjava.characteristics.impl.common.ActiveCharacteristic;
import io.github.hapjava.characteristics.impl.televisionspeaker.VolumeControlTypeCharacteristic;
import io.github.hapjava.characteristics.impl.televisionspeaker.VolumeSelectorCharacteristic;

/** This service describes a television. */
public class TelevisionSpeakerService extends AbstractServiceImpl {

  public TelevisionSpeakerService(MuteCharacteristic muteCharacteristic) {
    super("00000113-0000-1000-8000-0026BB765291");
    addCharacteristic(muteCharacteristic);
  }

  public TelevisionSpeakerService(TelevisionSpeakerAccessory accessory) {
    this(
        new MuteCharacteristic(
            accessory::isMuted,
            accessory::setMute,
            accessory::subscribeMuteState,
            accessory::unsubscribeMuteState));
    if (accessory instanceof AccessoryWithActive) {
      addOptionalCharacteristic(
          new ActiveCharacteristic(
              ((AccessoryWithActive) accessory)::getActive,
              ((AccessoryWithActive) accessory)::setActive,
              ((AccessoryWithActive) accessory)::subscribeActive,
              ((AccessoryWithActive) accessory)::unsubscribeActive));
    }
    if (accessory instanceof AccessoryWithVolume) {
      addOptionalCharacteristic(
          new VolumeCharacteristic(
              ((AccessoryWithVolume) accessory)::getVolume,
              ((AccessoryWithVolume) accessory)::setVolume,
              ((AccessoryWithVolume) accessory)::subscribeVolume,
              ((AccessoryWithVolume) accessory)::unsubscribeVolume));
    }
    if (accessory instanceof AccessoryWithVolumeSelector) {
      addOptionalCharacteristic(
          new VolumeSelectorCharacteristic(
              ((AccessoryWithVolumeSelector) accessory)::setVolumeSelector));
    }
    if (accessory instanceof AccessoryWithVolumeControlType) {
      addOptionalCharacteristic(
          new VolumeControlTypeCharacteristic(
              ((AccessoryWithVolumeControlType) accessory)::getVolumeControlType,
              ((AccessoryWithVolumeControlType) accessory)::subscribeVolumeControlType,
              ((AccessoryWithVolumeControlType) accessory)::unsubscribeVolumeControlType));
      ;
    }
  }

  public void addOptionalCharacteristic(ActiveCharacteristic activeCharacteristic) {
    addCharacteristic(activeCharacteristic);
  }

  public void addOptionalCharacteristic(VolumeCharacteristic volume) {
    addCharacteristic(volume);
  }

  public void addOptionalCharacteristic(VolumeSelectorCharacteristic volumeSelector) {
    addCharacteristic(volumeSelector);
  }

  public void addOptionalCharacteristic(VolumeControlTypeCharacteristic volumeControlType) {
    addCharacteristic(volumeControlType);
  }
}
