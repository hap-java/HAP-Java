package io.github.hapjava.impl.services;

import io.github.hapjava.accessories.LightSensor;
import io.github.hapjava.impl.characteristics.light.AmbientLightLevelCharacteristic;

public class LightSensorService extends AbstractServiceImpl {

  public LightSensorService(LightSensor lightSensor) {
    this(lightSensor, lightSensor.getLabel());
  }

  public LightSensorService(LightSensor lightSensor, String serviceName) {
    super("00000084-0000-1000-8000-0026BB765291", lightSensor, serviceName);
    addCharacteristic(new AmbientLightLevelCharacteristic(lightSensor));
  }
}
