package com.beowulfe.hap.impl.services;

import com.beowulfe.hap.accessories.BatteryAccessory;
import com.beowulfe.hap.accessories.MotionSensor;
import com.beowulfe.hap.impl.characteristics.common.BatteryLevelCharacteristic;
import com.beowulfe.hap.impl.characteristics.common.Name;
import com.beowulfe.hap.impl.characteristics.motionsensor.MotionDetectedStateCharacteristic;

public class MotionSensorService extends AbstractServiceImpl {

    public MotionSensorService(MotionSensor motionSensor) {
        super("00000085-0000-1000-8000-0026BB765291");
        addCharacteristic(new Name(motionSensor));
        addCharacteristic(new MotionDetectedStateCharacteristic(motionSensor));

        if (motionSensor instanceof BatteryAccessory) {
            BatteryAccessory batteryAccessory = (BatteryAccessory) motionSensor;
            addCharacteristic(new BatteryLevelCharacteristic(
                    batteryAccessory::getBatteryLevelState,
                    batteryAccessory::subscribeBatteryLevelState,
                    batteryAccessory::unsubscribeBatteryLevelState
            ));
        }
    }
}
