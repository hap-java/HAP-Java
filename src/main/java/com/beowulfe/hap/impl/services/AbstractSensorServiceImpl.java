package com.beowulfe.hap.impl.services;

import com.beowulfe.hap.accessories.AbstractSensor;
import com.beowulfe.hap.impl.characteristics.common.LowBatteryStatusCharacteristic;

public class AbstractSensorServiceImpl extends AbstractServiceImpl {
  public AbstractSensorServiceImpl(String type, AbstractSensor sensor, String serviceName) {
    super(type, sensor, serviceName);

    sensor
        .getLowBatteryStatusCharacteristic()
        .ifPresent(
            status ->
                addCharacteristic(
                    new LowBatteryStatusCharacteristic(
                        status::getLowBatteryState,
                        status::subscribeLowBatteryState,
                        status::unsubscribeLowBatteryState)));
  }
}
