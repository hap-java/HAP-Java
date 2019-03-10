package io.github.hapjava.impl.services;

import io.github.hapjava.accessories.CarbonMonoxideSensor;
import io.github.hapjava.impl.characteristics.carbonmonoxide.CarbonMonoxideDetectedCharacteristic;

public class CarbonMonoxideSensorService extends AbstractServiceImpl {

  public CarbonMonoxideSensorService(CarbonMonoxideSensor carbonMonoxideSensor) {
    this(carbonMonoxideSensor, carbonMonoxideSensor.getLabel());
  }

  public CarbonMonoxideSensorService(
      CarbonMonoxideSensor carbonMonoxideSensor, String serviceName) {
    super("0000007F-0000-1000-8000-0026BB765291", carbonMonoxideSensor, serviceName);
    addCharacteristic(new CarbonMonoxideDetectedCharacteristic(carbonMonoxideSensor));
  }
}
