package io.github.hapjava.services.impl;

import io.github.hapjava.accessories.LightbulbAccessory;
import io.github.hapjava.accessories.optionalcharacteristic.AccessoryWithBrightness;
import io.github.hapjava.accessories.optionalcharacteristic.AccessoryWithColor;
import io.github.hapjava.accessories.optionalcharacteristic.AccessoryWithColorTemperature;
import io.github.hapjava.accessories.optionalcharacteristic.AccessoryWithName;
import io.github.hapjava.characteristics.impl.common.NameCharacteristic;
import io.github.hapjava.characteristics.impl.common.OnCharacteristic;
import io.github.hapjava.characteristics.impl.lightbulb.BrightnessCharacteristic;
import io.github.hapjava.characteristics.impl.lightbulb.ColorTemperatureCharacteristic;
import io.github.hapjava.characteristics.impl.lightbulb.HueCharacteristic;
import io.github.hapjava.characteristics.impl.lightbulb.SaturationCharacteristic;

/** This service describes a light bulb. */
public class LightbulbService extends AbstractServiceImpl {

  public LightbulbService(OnCharacteristic onState) {
    super("00000043-0000-1000-8000-0026BB765291");
    addCharacteristic(onState);
  }

  public LightbulbService(LightbulbAccessory accessory) {
    this(
        new OnCharacteristic(
            accessory::getLightbulbPowerState,
            accessory::setLightbulbPowerState,
            accessory::subscribeLightbulbPowerState,
            accessory::unsubscribeLightbulbPowerState));
    if (accessory instanceof AccessoryWithName) {
      addOptionalCharacteristic(new NameCharacteristic(((AccessoryWithName) accessory)::getName));
    }
    if (accessory instanceof AccessoryWithColor) {
      addOptionalCharacteristic(
          new HueCharacteristic(
              ((AccessoryWithColor) accessory)::getHue,
              ((AccessoryWithColor) accessory)::setHue,
              ((AccessoryWithColor) accessory)::subscribeHue,
              ((AccessoryWithColor) accessory)::unsubscribeHue));
      addOptionalCharacteristic(
          new SaturationCharacteristic(
              ((AccessoryWithColor) accessory)::getSaturation,
              ((AccessoryWithColor) accessory)::setSaturation,
              ((AccessoryWithColor) accessory)::subscribeSaturation,
              ((AccessoryWithColor) accessory)::unsubscribeSaturation));
    }
    if (accessory instanceof AccessoryWithBrightness) {
      addOptionalCharacteristic(
          new BrightnessCharacteristic(
              ((AccessoryWithBrightness) accessory)::getBrightness,
              ((AccessoryWithBrightness) accessory)::setBrightness,
              ((AccessoryWithBrightness) accessory)::subscribeBrightness,
              ((AccessoryWithBrightness) accessory)::unsubscribeBrightness));
    }
    if (accessory instanceof AccessoryWithColorTemperature) {
      addOptionalCharacteristic(
          new ColorTemperatureCharacteristic(
              ((AccessoryWithColorTemperature) accessory).getMinColorTemperature(),
              ((AccessoryWithColorTemperature) accessory).getMaxColorTemperature(),
              ((AccessoryWithColorTemperature) accessory)::getColorTemperature,
              ((AccessoryWithColorTemperature) accessory)::setColorTemperature,
              ((AccessoryWithColorTemperature) accessory)::subscribeColorTemperature,
              ((AccessoryWithColorTemperature) accessory)::unsubscribeColorTemperature));
    }
  }

  public void addOptionalCharacteristic(NameCharacteristic name) {
    addCharacteristic(name);
  }

  public void addOptionalCharacteristic(HueCharacteristic hue) {
    addCharacteristic(hue);
  }

  public void addOptionalCharacteristic(BrightnessCharacteristic brightness) {
    addCharacteristic(brightness);
  }

  public void addOptionalCharacteristic(SaturationCharacteristic saturation) {
    addCharacteristic(saturation);
  }

  public void addOptionalCharacteristic(ColorTemperatureCharacteristic color) {
    addCharacteristic(color);
  }
}
