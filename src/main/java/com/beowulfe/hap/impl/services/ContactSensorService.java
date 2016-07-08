package com.beowulfe.hap.impl.services;

import com.beowulfe.hap.accessories.BatteryAccessory;
import com.beowulfe.hap.accessories.ContactSensor;
import com.beowulfe.hap.impl.characteristics.common.BatteryLevelCharacteristic;
import com.beowulfe.hap.impl.characteristics.common.Name;
import com.beowulfe.hap.impl.characteristics.contactsensor.ContactSensorStateCharacteristic;

public class ContactSensorService extends AbstractServiceImpl {

    public ContactSensorService(ContactSensor contactSensor) {
        super("00000080-0000-1000-8000-0026BB765291");
        addCharacteristic(new Name(contactSensor));
        addCharacteristic(new ContactSensorStateCharacteristic(contactSensor));

        if (contactSensor instanceof BatteryAccessory) {
            BatteryAccessory batteryAccessory = (BatteryAccessory) contactSensor;
            addCharacteristic(new BatteryLevelCharacteristic(
                    batteryAccessory::getBatteryLevelState,
                    batteryAccessory::subscribeBatteryLevelState,
                    batteryAccessory::unsubscribeBatteryLevelState
            ));
        }
    }
}
