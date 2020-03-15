package io.github.hapjava.accessories;

import io.github.hapjava.characteristics.HomekitCharacteristicChangeCallback;
import io.github.hapjava.characteristics.impl.smokesensor.SmokeDetectedStateEnum;
import io.github.hapjava.services.Service;
import io.github.hapjava.services.impl.SmokeSensorService;
import java.util.Collection;
import java.util.Collections;
import java.util.concurrent.CompletableFuture;

/**
 * A smoke sensor reports whether smoke has been detected or not.
 *
 * <p>Smoke sensors that run on batteries will need to implement this interface and also implement
 * {@link BatteryAccessory}.
 *
 * @author Gaston Dombiak
 */
public interface SmokeSensorAccessory extends HomekitAccessory {

  /**
   * Retrieves the state of the smoke sensor. This is whether smoke has been detected or not.
   *
   * @return a future that will contain the smoke sensor's state
   */
  CompletableFuture<SmokeDetectedStateEnum> getSmokeDetectedState();

  /**
   * Subscribes to changes in the smoke sensor's state.
   *
   * @param callback the function to call when the state changes.
   */
  void subscribeSmokeDetectedState(HomekitCharacteristicChangeCallback callback);

  /** Unsubscribes from changes in the smoke sensor's state. */
  void unsubscribeSmokeDetectedState();

  @Override
  default Collection<Service> getServices() {
    return Collections.singleton(new SmokeSensorService(this));
  }
}
