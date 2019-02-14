package com.beowulfe.hap.accessories;

import com.beowulfe.hap.HomekitCharacteristicChangeCallback;
import com.beowulfe.hap.Service;
import com.beowulfe.hap.accessories.properties.ContactState;
import com.beowulfe.hap.impl.services.ContactSensorService;
import java.util.Collection;
import java.util.Collections;
import java.util.concurrent.CompletableFuture;

/**
 * A contact sensor that reports whether contact is detected or not. Typical contact sensors are
 * window/door sensors. When contact is detected it means that the door/window is closed.
 *
 * <p>Contact sensors that run on batteries will need to implement this interface and also implement
 * LowBatteryStatus.
 *
 * @author Gaston Dombiak
 */
public interface ContactSensor extends AbstractSensor {

  /**
   * Retrieves the state of the contact. This is whether the contact is detected or not. Detected
   * contact means door/window is closed.
   *
   * @return a future that will contain the contact's state
   */
  CompletableFuture<ContactState> getCurrentState();

  @Override
  default Collection<Service> getServices() {
    return Collections.singleton(new ContactSensorService(this));
  }

  /**
   * Subscribes to changes in the contact state.
   *
   * @param callback the function to call when the state changes.
   */
  void subscribeContactState(HomekitCharacteristicChangeCallback callback);

  /** Unsubscribes from changes in the contact state. */
  void unsubscribeContactState();
}
