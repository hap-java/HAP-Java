package com.beowulfe.hap.accessories;

import com.beowulfe.hap.HomekitCharacteristicChangeCallback;
import com.beowulfe.hap.Service;
import com.beowulfe.hap.impl.services.LeakSensorService;
import java.util.Collection;
import java.util.Collections;
import java.util.concurrent.CompletableFuture;

/**
 * A leak sensor that reports whether a leak has been detected.
 *
 * <p>Leak sensors that run on batteries will need to implement this interface and also implement
 * LowBatteryStatus.
 *
 * @author Tim Harper
 */
public interface LeakSensor extends AbstractSensor {

  /**
   * Retrieves the state of the leak sensor. If true then leak has been detected.
   *
   * @return a future that will contain the leak sensor's state
   */
  CompletableFuture<Boolean> getLeakDetected();

  @Override
  default Collection<Service> getServices() {
    return Collections.singleton(new LeakSensorService(this));
  }

  /**
   * Subscribes to changes in the leak sensor.
   *
   * @param callback the function to call when the state changes.
   */
  void subscribeLeakDetected(HomekitCharacteristicChangeCallback callback);

  /** Unsubscribes from changes in the leak sensor. */
  void unsubscribeLeakDetected();
}
