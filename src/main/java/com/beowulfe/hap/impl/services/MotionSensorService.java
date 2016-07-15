package com.beowulfe.hap.impl.services;

import com.beowulfe.hap.accessories.MotionSensor;
import com.beowulfe.hap.impl.characteristics.motionsensor.MotionDetectedStateCharacteristic;

public class MotionSensorService extends AbstractServiceImpl {

    public MotionSensorService(MotionSensor motionSensor) {
        super("00000085-0000-1000-8000-0026BB765291", motionSensor);
        addCharacteristic(new MotionDetectedStateCharacteristic(motionSensor));
    }
}
