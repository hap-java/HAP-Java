package com.beowulfe.hap.impl.services;

import com.beowulfe.hap.accessories.SmokeSensor;
import com.beowulfe.hap.impl.characteristics.smokesensor.SmokeDetectedCharacteristic;

public class SmokeSensorService extends AbstractServiceImpl {

    public SmokeSensorService(SmokeSensor smokeSensor) {
        super("00000087-0000-1000-8000-0026BB765291", smokeSensor);
        addCharacteristic(new SmokeDetectedCharacteristic(smokeSensor));
    }
}
