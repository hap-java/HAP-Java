package io.github.hapjava.accessories;

import io.github.hapjava.characteristics.HomekitCharacteristicChangeCallback;
import io.github.hapjava.characteristics.impl.carbondioxidesensor.CarbonDioxideDetectedEnum;
import io.github.hapjava.services.Service;
import io.github.hapjava.services.impl.CarbonDioxideSensorService;
import java.util.Collection;
import java.util.Collections;
import java.util.concurrent.CompletableFuture;

/**
 * A carbon dioxide sensor reports whether carbon dioxide has been detected or not.
 *
 * <p>Carbon dioxide sensors that run on batteries will need to implement this interface and also
 * implement {@link BatteryAccessory}.
 *
 * @author Eugen Freiter
 */
public interface CarbonDioxideSensorAccessory extends HomekitAccessory {

  /**
   * Retrieves the state of the sensor that indicates if carbon dioxide has been detected.
   *
   * @return a future that will contain the carbon dioxide sensor's state
   */
  CompletableFuture<CarbonDioxideDetectedEnum> getCarbonDioxideDetectedState();

  /**
   * Subscribes to changes in the carbon dioxide's state.
   *
   * @param callback the function to call when the state changes.
   */
  void subscribeCarbonDioxideDetectedState(HomekitCharacteristicChangeCallback callback);

  /** Unsubscribes from changes in the carbon dioxide's state. */
  void unsubscribeCarbonDioxideDetectedState();

  @Override
  default Collection<Service> getServices() {
    return Collections.singleton(new CarbonDioxideSensorService(this));
  }
}
