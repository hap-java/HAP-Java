package com.beowulfe.hap.impl.services;

import com.beowulfe.hap.accessories.CarbonMonoxideSensor;
import com.beowulfe.hap.impl.characteristics.carbonmonoxide.CarbonMonoxideDetectedCharacteristic;

public class CarbonMonoxideSensorService extends AbstractServiceImpl {

    public CarbonMonoxideSensorService(CarbonMonoxideSensor carbonMonoxideSensor) {
        this(carbonMonoxideSensor, carbonMonoxideSensor.getLabel());
    }

    public CarbonMonoxideSensorService(CarbonMonoxideSensor carbonMonoxideSensor, String serviceName) {
        super("0000007F-0000-1000-8000-0026BB765291", carbonMonoxideSensor, serviceName);
        addCharacteristic(new CarbonMonoxideDetectedCharacteristic(carbonMonoxideSensor));
    }
}
