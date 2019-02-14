package com.beowulfe.hap.accessories;

import com.beowulfe.hap.HomekitCharacteristicChangeCallback;
import com.beowulfe.hap.Service;
import com.beowulfe.hap.accessories.properties.CarbonMonoxideDetectedState;
import com.beowulfe.hap.impl.services.CarbonMonoxideSensorService;
import java.util.Collection;
import java.util.Collections;
import java.util.concurrent.CompletableFuture;

/**
 * A carbon monoxide sensor reports whether carbon monoxide has been detected or not.
 *
 * <p>Carbon monoxide sensors that run on batteries will need to implement this interface and also
 * implement LowBatteryStatus.
 *
 * @author Gaston Dombiak
 */
public interface CarbonMonoxideSensor extends AbstractSensor {

  /**
   * Retrieves the state of the sensor that indicates if carbon monoxide has been detected.
   *
   * @return a future that will contain the carbon monoxide sensor's state
   */
  CompletableFuture<CarbonMonoxideDetectedState> getCarbonMonoxideDetectedState();

  @Override
  default Collection<Service> getServices() {
    return Collections.singleton(new CarbonMonoxideSensorService(this));
  }

  /**
   * Subscribes to changes in the carbon monoxide's state.
   *
   * @param callback the function to call when the state changes.
   */
  void subscribeCarbonMonoxideDetectedState(HomekitCharacteristicChangeCallback callback);

  /** Unsubscribes from changes in the carbon monoxide's state. */
  void unsubscribeCarbonMonoxideDetectedState();
}
