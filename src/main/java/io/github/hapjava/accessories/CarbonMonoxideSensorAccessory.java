package io.github.hapjava.accessories;

import io.github.hapjava.characteristics.HomekitCharacteristicChangeCallback;
import io.github.hapjava.characteristics.impl.carbonmonoxidesensor.CarbonMonoxideDetectedEnum;
import io.github.hapjava.services.Service;
import io.github.hapjava.services.impl.CarbonMonoxideSensorService;
import java.util.Collection;
import java.util.Collections;
import java.util.concurrent.CompletableFuture;

/**
 * A carbon monoxide sensor reports whether carbon monoxide has been detected or not.
 *
 * <p>Carbon monoxide sensors that run on batteries will need to implement this interface and also
 * implement {@link BatteryAccessory}.
 *
 * @author Gaston Dombiak
 */
public interface CarbonMonoxideSensorAccessory extends HomekitAccessory {

  /**
   * Retrieves the state of the sensor that indicates if carbon monoxide has been detected.
   *
   * @return a future that will contain the carbon monoxide sensor's state
   */
  CompletableFuture<CarbonMonoxideDetectedEnum> getCarbonMonoxideDetectedState();

  /**
   * Subscribes to changes in the carbon monoxide's state.
   *
   * @param callback the function to call when the state changes.
   */
  void subscribeCarbonMonoxideDetectedState(HomekitCharacteristicChangeCallback callback);

  /** Unsubscribes from changes in the carbon monoxide's state. */
  void unsubscribeCarbonMonoxideDetectedState();

  @Override
  default Collection<Service> getServices() {
    return Collections.singleton(new CarbonMonoxideSensorService(this));
  }
}
