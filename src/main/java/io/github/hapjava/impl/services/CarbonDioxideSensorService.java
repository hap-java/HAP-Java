package io.github.hapjava.impl.services;

import io.github.hapjava.accessories.CarbonDioxideSensor;
import io.github.hapjava.impl.characteristics.carbondioxide.CarbonDioxideDetectedCharacteristic;
import io.github.hapjava.impl.characteristics.carbondioxide.CarbonDioxideLevelCharacteristic;

public class CarbonDioxideSensorService extends AbstractServiceImpl {

  public CarbonDioxideSensorService(CarbonDioxideSensor carbonDioxideSensor) {
    this(carbonDioxideSensor, carbonDioxideSensor.getLabel());
  }

  public CarbonDioxideSensorService(CarbonDioxideSensor carbonDioxideSensor, String serviceName) {
    super("00000097-0000-1000-8000-0026BB765291", carbonDioxideSensor, serviceName);
    addCharacteristic(new CarbonDioxideDetectedCharacteristic(carbonDioxideSensor));
    addCharacteristic(new CarbonDioxideLevelCharacteristic(carbonDioxideSensor));
  }
}
