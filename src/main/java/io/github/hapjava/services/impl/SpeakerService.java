package io.github.hapjava.services.impl;

import io.github.hapjava.accessories.SpeakerAccessory;
import io.github.hapjava.accessories.optionalcharacteristic.AccessoryWithName;
import io.github.hapjava.accessories.optionalcharacteristic.AccessoryWithVolume;
import io.github.hapjava.characteristics.impl.audio.MuteCharacteristic;
import io.github.hapjava.characteristics.impl.audio.VolumeCharacteristic;
import io.github.hapjava.characteristics.impl.common.NameCharacteristic;

/** A Speaker service can be used to control the audio output settings on a speaker device. */
public class SpeakerService extends AbstractServiceImpl {

  public SpeakerService(MuteCharacteristic muteCharacteristic) {
    super("00000113-0000-1000-8000-0026BB765291");
    addCharacteristic(muteCharacteristic);
  }

  public SpeakerService(SpeakerAccessory accessory) {
    this(
        new MuteCharacteristic(
            accessory::isMuted,
            accessory::setMute,
            accessory::subscribeMuteState,
            accessory::unsubscribeMuteState));
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
  }

  public void addOptionalCharacteristic(NameCharacteristic name) {
    addCharacteristic(name);
  }

  public void addOptionalCharacteristic(VolumeCharacteristic volume) {
    addCharacteristic(volume);
  }
}
