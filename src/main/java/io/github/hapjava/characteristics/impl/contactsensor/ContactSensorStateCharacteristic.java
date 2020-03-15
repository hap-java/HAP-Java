package io.github.hapjava.characteristics.impl.contactsensor;

import io.github.hapjava.characteristics.EventableCharacteristic;
import io.github.hapjava.characteristics.HomekitCharacteristicChangeCallback;
import io.github.hapjava.characteristics.impl.base.EnumCharacteristic;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * This characteristic describes the state of a door/window contact sensor. A value of 0 indicates
 * that the contact is detected. A value of 1 indicates that the contact is not detected.
 */
public class ContactSensorStateCharacteristic extends EnumCharacteristic<ContactStateEnum>
    implements EventableCharacteristic {

  public ContactSensorStateCharacteristic(
      Supplier<CompletableFuture<ContactStateEnum>> getter,
      Consumer<HomekitCharacteristicChangeCallback> subscriber,
      Runnable unsubscriber) {
    super(
        "0000006A-0000-1000-8000-0026BB765291",
        "Contact Sensor State",
        1,
        Optional.of(getter),
        Optional.empty(),
        Optional.of(subscriber),
        Optional.of(unsubscriber));
  }
}
