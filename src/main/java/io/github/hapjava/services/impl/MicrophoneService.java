package io.github.hapjava.services.impl;

import io.github.hapjava.accessories.MicrophoneAccessory;
import io.github.hapjava.accessories.optionalcharacteristic.AccessoryWithName;
import io.github.hapjava.accessories.optionalcharacteristic.AccessoryWithVolume;
import io.github.hapjava.characteristics.impl.audio.MuteCharacteristic;
import io.github.hapjava.characteristics.impl.audio.MuteEnum;
import io.github.hapjava.characteristics.impl.audio.VolumeCharacteristic;
import io.github.hapjava.characteristics.impl.common.NameCharacteristic;

/**
 * A Microphone service is used to control the sourcing of the input audio – primarily through a
 * microphone.
 */
public class MicrophoneService extends AbstractServiceImpl {

  public MicrophoneService(MuteCharacteristic muteCharacteristic) {
    super("00000112-0000-1000-8000-0026BB765291");
    addCharacteristic(muteCharacteristic);
  }

  public MicrophoneService(MicrophoneAccessory accessory) {
    this(
        new MuteCharacteristic(
            () -> accessory.isMuted().thenApply(s -> s ? MuteEnum.ON : MuteEnum.OFF),
            (v) -> accessory.setMute(v == MuteEnum.ON),
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
