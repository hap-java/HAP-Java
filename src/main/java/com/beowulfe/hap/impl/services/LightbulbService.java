package com.beowulfe.hap.impl.services;

import com.beowulfe.hap.accessories.Lightbulb;
import com.beowulfe.hap.impl.characteristics.common.PowerStateCharacteristic;
import com.beowulfe.hap.impl.characteristics.lightbulb.BrightnessCharacteristic;
import com.beowulfe.hap.impl.characteristics.lightbulb.HueCharacteristic;
import com.beowulfe.hap.impl.characteristics.lightbulb.SaturationCharacteristic;

public class LightbulbService extends AbstractServiceImpl {

  public LightbulbService(Lightbulb lightbulb) {
    this(lightbulb, lightbulb.getLabel());
  }

  public LightbulbService(Lightbulb lightbulb, String serviceName) {
    super("00000043-0000-1000-8000-0026BB765291", lightbulb, serviceName);
    addCharacteristic(
        new PowerStateCharacteristic(
            () -> lightbulb.getLightbulbPowerState(),
            v -> lightbulb.setLightbulbPowerState(v),
            c -> lightbulb.subscribeLightbulbPowerState(c),
            () -> lightbulb.unsubscribeLightbulbPowerState()));

    lightbulb
        .getBrightnessCharacteristic()
        .ifPresent(brightness -> addCharacteristic(new BrightnessCharacteristic(brightness)));

    lightbulb
        .getColorCharacteristics()
        .ifPresent(
            color -> {
              addCharacteristic(new HueCharacteristic(color));
              addCharacteristic(new SaturationCharacteristic(color));
            });
  }
}
