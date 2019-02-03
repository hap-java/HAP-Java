package com.beowulfe.hap.impl.services;

import com.beowulfe.hap.accessories.OccupancySensor;
import com.beowulfe.hap.impl.characteristics.occupancysensor.OccupancyDetectedStateCharacteristic;

public class OccupancySensorService extends AbstractServiceImpl {

  public OccupancySensorService(OccupancySensor occupancySensor) {
    this(occupancySensor, occupancySensor.getLabel());
  }

  public OccupancySensorService(OccupancySensor occupancySensor, String serviceName) {
    super("00000086-0000-1000-8000-0026BB765291", occupancySensor, serviceName);
    addCharacteristic(new OccupancyDetectedStateCharacteristic(occupancySensor));
  }
}
