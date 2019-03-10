package io.github.hapjava.impl.services;

import io.github.hapjava.accessories.SmokeSensor;
import io.github.hapjava.impl.characteristics.smokesensor.SmokeDetectedCharacteristic;

public class SmokeSensorService extends AbstractServiceImpl {

  public SmokeSensorService(SmokeSensor smokeSensor) {
    this(smokeSensor, smokeSensor.getLabel());
  }

  public SmokeSensorService(SmokeSensor smokeSensor, String serviceName) {
    super("00000087-0000-1000-8000-0026BB765291", smokeSensor, serviceName);
    addCharacteristic(new SmokeDetectedCharacteristic(smokeSensor));
  }
}
