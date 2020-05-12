package io.github.hapjava.accessories;

import io.github.hapjava.characteristics.HomekitCharacteristicChangeCallback;
import io.github.hapjava.services.Service;
import io.github.hapjava.services.impl.MotionSensorService;
import java.util.Collection;
import java.util.Collections;
import java.util.concurrent.CompletableFuture;

/**
 * A motion sensor that reports whether motion has been detected.
 *
 * @author Gaston Dombiak
 */
public interface MotionSensorAccessory extends HomekitAccessory {

  /**
   * Retrieves the state of the motion sensor. If true then motion has been detected.
   *
   * @return a future that will contain the motion sensor's state
   */
  CompletableFuture<Boolean> getMotionDetected();

  /**
   * Subscribes to changes in the motion sensor.
   *
   * @param callback the function to call when the state changes.
   */
  void subscribeMotionDetected(HomekitCharacteristicChangeCallback callback);

  /** Unsubscribes from changes in the motion sensor. */
  void unsubscribeMotionDetected();

  @Override
  default Collection<Service> getServices() {
    return Collections.singleton(new MotionSensorService(this));
  }
}
