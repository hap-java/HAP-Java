package com.beowulfe.hap.impl.services;

import com.beowulfe.hap.accessories.LeakSensor;
import com.beowulfe.hap.impl.characteristics.leaksensor.LeakDetectedStateCharacteristic;

public class LeakSensorService extends AbstractServiceImpl {

  public LeakSensorService(LeakSensor leakSensor) {
    this(leakSensor, leakSensor.getLabel());
  }

  public LeakSensorService(LeakSensor leakSensor, String serviceName) {
    super("00000083-0000-1000-8000-0026BB765291", leakSensor, serviceName);
    addCharacteristic(new LeakDetectedStateCharacteristic(leakSensor));
  }
}
