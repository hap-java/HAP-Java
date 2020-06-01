package io.github.hapjava.accessories;

import io.github.hapjava.characteristics.HomekitCharacteristicChangeCallback;
import io.github.hapjava.services.Service;
import io.github.hapjava.services.impl.LightSensorService;
import java.util.Collection;
import java.util.Collections;
import java.util.concurrent.CompletableFuture;

/**
 * A light sensor that reports current ambient light level.
 *
 * @author Gaston Dombiak
 */
public interface LightSensorAccessory extends HomekitAccessory {

  /**
   * Retrieves the current ambient light level.
   *
   * @return a future that will contain the luminance level expressed in LUX.
   */
  CompletableFuture<Double> getCurrentAmbientLightLevel();

  /**
   * Subscribes to changes in the current ambient light level.
   *
   * @param callback the function to call when the state changes.
   */
  void subscribeCurrentAmbientLightLevel(HomekitCharacteristicChangeCallback callback);

  /** Unsubscribes from changes in the current ambient light level. */
  void unsubscribeCurrentAmbientLightLevel();

  @Override
  default Collection<Service> getServices() {
    return Collections.singleton(new LightSensorService(this));
  }
}
