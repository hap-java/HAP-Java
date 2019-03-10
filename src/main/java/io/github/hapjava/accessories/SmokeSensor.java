package io.github.hapjava.accessories;

import io.github.hapjava.HomekitAccessory;
import io.github.hapjava.HomekitCharacteristicChangeCallback;
import io.github.hapjava.Service;
import io.github.hapjava.accessories.properties.SmokeDetectedState;
import io.github.hapjava.impl.services.SmokeSensorService;
import java.util.Collection;
import java.util.Collections;
import java.util.concurrent.CompletableFuture;

/**
 * A smoke sensor reports whether smoke has been detected or not.
 *
 * <p>Smoke sensors that run on batteries will need to implement this interface and also implement
 * {@link BatteryStatusAccessory}.
 *
 * @author Gaston Dombiak
 */
public interface SmokeSensor extends HomekitAccessory {

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
