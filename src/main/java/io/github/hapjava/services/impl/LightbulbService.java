package io.github.hapjava.services.impl;

import io.github.hapjava.accessories.LightbulbAccessory;
import io.github.hapjava.characteristics.impl.accessoryinformation.NameCharacteristic;
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

  public LightbulbService(LightbulbAccessory lightbulb) {
    this(
        new OnCharacteristic(
            lightbulb::getLightbulbPowerState,
            lightbulb::setLightbulbPowerState,
            lightbulb::subscribeLightbulbPowerState,
            lightbulb::unsubscribeLightbulbPowerState));
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
