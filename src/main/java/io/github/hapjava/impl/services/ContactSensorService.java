package io.github.hapjava.impl.services;

import io.github.hapjava.accessories.ContactSensor;
import io.github.hapjava.impl.characteristics.contactsensor.ContactSensorStateCharacteristic;

public class ContactSensorService extends AbstractServiceImpl {

  public ContactSensorService(ContactSensor contactSensor) {
    this(contactSensor, contactSensor.getLabel());
  }

  public ContactSensorService(ContactSensor contactSensor, String serviceName) {
    super("00000080-0000-1000-8000-0026BB765291", contactSensor, serviceName);
    addCharacteristic(new ContactSensorStateCharacteristic(contactSensor));
  }
}
