package com.beowulfe.hap.accessories;

import com.beowulfe.hap.HomekitCharacteristicChangeCallback;
import com.beowulfe.hap.Service;
import com.beowulfe.hap.accessories.properties.SmokeDetectedState;
import com.beowulfe.hap.impl.services.SmokeSensorService;
import java.util.Collection;
import java.util.Collections;
import java.util.concurrent.CompletableFuture;

/**
 * A smoke sensor reports whether smoke has been detected or not.
 *
 * @author Gaston Dombiak
 */
public interface SmokeSensor extends AbstractSensor {

  /**
   * Retrieves the state of the smoke sensor. This is whether smoke has been detected or not.
   *
   * @return a future that will contain the smoke sensor's state
   */
  CompletableFuture<SmokeDetectedState> getSmokeDetectedState();

  @Override
  default Collection<Service> getServices() {
    return Collections.singleton(new SmokeSensorService(this));
  }

  /**
   * Subscribes to changes in the smoke sensor's state.
   *
   * @param callback the function to call when the state changes.
   */
  void subscribeSmokeDetectedState(HomekitCharacteristicChangeCallback callback);

  /** Unsubscribes from changes in the smoke sensor's state. */
  void unsubscribeSmokeDetectedState();
}
