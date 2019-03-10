package io.github.hapjava.impl.services;

import io.github.hapjava.accessories.ColorfulLightbulb;
import io.github.hapjava.accessories.DimmableLightbulb;
import io.github.hapjava.accessories.Lightbulb;
import io.github.hapjava.impl.characteristics.common.PowerStateCharacteristic;
import io.github.hapjava.impl.characteristics.lightbulb.BrightnessCharacteristic;
import io.github.hapjava.impl.characteristics.lightbulb.HueCharacteristic;
import io.github.hapjava.impl.characteristics.lightbulb.SaturationCharacteristic;

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

    if (lightbulb instanceof DimmableLightbulb) {
      addCharacteristic(new BrightnessCharacteristic((DimmableLightbulb) lightbulb));
    }

    if (lightbulb instanceof ColorfulLightbulb) {
      addCharacteristic(new HueCharacteristic((ColorfulLightbulb) lightbulb));
      addCharacteristic(new SaturationCharacteristic((ColorfulLightbulb) lightbulb));
    }
  }
}
