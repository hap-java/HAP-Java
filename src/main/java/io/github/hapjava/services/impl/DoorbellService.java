package io.github.hapjava.services.impl;

import io.github.hapjava.accessories.DoorbellAccessory;
import io.github.hapjava.accessories.optionalcharacteristic.AccessoryWithBrightness;
import io.github.hapjava.accessories.optionalcharacteristic.AccessoryWithMute;
import io.github.hapjava.accessories.optionalcharacteristic.AccessoryWithName;
import io.github.hapjava.accessories.optionalcharacteristic.AccessoryWithVolume;
import io.github.hapjava.characteristics.impl.audio.MuteCharacteristic;
import io.github.hapjava.characteristics.impl.audio.VolumeCharacteristic;
import io.github.hapjava.characteristics.impl.common.NameCharacteristic;
import io.github.hapjava.characteristics.impl.common.ProgrammableSwitchEventCharacteristic;
import io.github.hapjava.characteristics.impl.lightbulb.BrightnessCharacteristic;

/** The Doorbell service describes a doorbell. */
public class DoorbellService extends AbstractServiceImpl {

  public DoorbellService(ProgrammableSwitchEventCharacteristic switchEvent) {
    super("00000121-0000-1000-8000-0026BB765291");
    addCharacteristic(switchEvent);
  }

  public DoorbellService(DoorbellAccessory accessory) {
    this(
        new ProgrammableSwitchEventCharacteristic(
            accessory::getSwitchEvent,
            accessory::subscribeSwitchEvent,
            accessory::unsubscribeSwitchEvent));
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
    if (accessory instanceof AccessoryWithVolume) {
      addOptionalCharacteristic(
          new VolumeCharacteristic(
              ((AccessoryWithVolume) accessory)::getVolume,
              ((AccessoryWithVolume) accessory)::setVolume,
              ((AccessoryWithVolume) accessory)::subscribeVolume,
              ((AccessoryWithVolume) accessory)::unsubscribeVolume));
    }
    if (accessory instanceof AccessoryWithMute) {
      addOptionalCharacteristic(
          new MuteCharacteristic(
              ((AccessoryWithMute) accessory)::isMuted,
              ((AccessoryWithMute) accessory)::setMute,
              ((AccessoryWithMute) accessory)::subscribeMuteState,
              ((AccessoryWithMute) accessory)::unsubscribeMuteState));
    }
  }

  public void addOptionalCharacteristic(NameCharacteristic name) {
    addCharacteristic(name);
  }

  public void addOptionalCharacteristic(BrightnessCharacteristic brightness) {
    addCharacteristic(brightness);
  }

  public void addOptionalCharacteristic(VolumeCharacteristic volume) {
    addCharacteristic(volume);
  }

  public void addOptionalCharacteristic(MuteCharacteristic mute) {
    addCharacteristic(mute);
  }
}
