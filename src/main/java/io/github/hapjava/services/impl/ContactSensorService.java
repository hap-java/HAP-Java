package io.github.hapjava.services.impl;

import io.github.hapjava.accessories.ContactSensorAccessory;
import io.github.hapjava.characteristics.impl.battery.StatusLowBatteryCharacteristic;
import io.github.hapjava.characteristics.impl.common.StatusActiveCharacteristic;
import io.github.hapjava.characteristics.impl.common.StatusFaultCharacteristic;
import io.github.hapjava.characteristics.impl.common.StatusTamperedCharacteristic;
import io.github.hapjava.characteristics.impl.contactsensor.ContactSensorStateCharacteristic;

/** This service describes a Contact Sensor. */
public class ContactSensorService extends AbstractServiceImpl {

  public ContactSensorService(ContactSensorStateCharacteristic contactSensorState) {
    super("00000080-0000-1000-8000-0026BB765291");
    addCharacteristic(contactSensorState);
  }

  public ContactSensorService(ContactSensorAccessory contactSensor) {
    this(
        new ContactSensorStateCharacteristic(
            contactSensor::getCurrentState,
            contactSensor::subscribeContactState,
            contactSensor::unsubscribeContactState));
  }

  public void addOptionalCharacteristic(StatusActiveCharacteristic statusActive) {
    addCharacteristic(statusActive);
  }

  public void addOptionalCharacteristic(StatusFaultCharacteristic statusFault) {
    addCharacteristic(statusFault);
  }

  public void addOptionalCharacteristic(StatusTamperedCharacteristic statusTampered) {
    addCharacteristic(statusTampered);
  }

  public void addOptionalCharacteristic(StatusLowBatteryCharacteristic statusLowBattery) {
    addCharacteristic(statusLowBattery);
  }
}
