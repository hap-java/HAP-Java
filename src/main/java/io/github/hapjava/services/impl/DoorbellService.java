package io.github.hapjava.services.impl;

import io.github.hapjava.accessories.DoorbellAccessory;
import io.github.hapjava.accessories.optionalcharacteristic.AccessoryWithBrightness;
import io.github.hapjava.accessories.optionalcharacteristic.AccessoryWithName;
import io.github.hapjava.accessories.optionalcharacteristic.AccessoryWithVolume;
import io.github.hapjava.characteristics.impl.audio.VolumeCharacteristic;
import io.github.hapjava.characteristics.impl.common.NameCharacteristic;
import io.github.hapjava.characteristics.impl.common.ProgrammableSwitchEventCharacteristic;
import io.github.hapjava.characteristics.impl.lightbulb.BrightnessCharacteristic;

/**
 * The Doorbell service describes a doorbell and is the primary service of the Video Doorbell
 * Profile.
 *
 * @author Eugen Freiter
 */
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
}
