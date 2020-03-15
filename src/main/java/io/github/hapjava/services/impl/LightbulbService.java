package io.github.hapjava.services.impl;

import io.github.hapjava.accessories.LightbulbAccessory;
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
