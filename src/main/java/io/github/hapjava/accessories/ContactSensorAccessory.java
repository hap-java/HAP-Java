package io.github.hapjava.accessories;

import io.github.hapjava.characteristics.HomekitCharacteristicChangeCallback;
import io.github.hapjava.characteristics.impl.contactsensor.ContactStateEnum;
import io.github.hapjava.services.Service;
import io.github.hapjava.services.impl.ContactSensorService;
import java.util.Collection;
import java.util.Collections;
import java.util.concurrent.CompletableFuture;

/**
 * A contact sensor that reports whether contact is detected or not. Typical contact sensors are
 * window/door sensors. When contact is detected it means that the door/window is closed.
 *
 * <p>Contact sensors that run on batteries will need to implement this interface and also implement
 * {@link BatteryAccessory}.
 *
 * @author Gaston Dombiak
 */
public interface ContactSensorAccessory extends HomekitAccessory {

  /**
   * Retrieves the state of the contact. This is whether the contact is detected or not. Detected
   * contact means door/window is closed.
   *
   * @return a future that will contain the contact's state
   */
  CompletableFuture<ContactStateEnum> getCurrentState();

  /**
   * Subscribes to changes in the contact state.
   *
   * @param callback the function to call when the state changes.
   */
  void subscribeContactState(HomekitCharacteristicChangeCallback callback);

  /** Unsubscribes from changes in the contact state. */
  void unsubscribeContactState();

  @Override
  default Collection<Service> getServices() {
    return Collections.singleton(new ContactSensorService(this));
  }
}
