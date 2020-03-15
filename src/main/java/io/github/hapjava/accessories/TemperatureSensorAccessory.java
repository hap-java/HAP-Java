package io.github.hapjava.accessories;

import io.github.hapjava.characteristics.HomekitCharacteristicChangeCallback;
import io.github.hapjava.services.Service;
import io.github.hapjava.services.impl.TemperatureSensorService;
import java.util.Collection;
import java.util.Collections;
import java.util.concurrent.CompletableFuture;

/**
 * A temperature sensor that reports the current temperature
 *
 * @author Andy Lintner
 */
public interface TemperatureSensorAccessory extends HomekitAccessory {

  /**
   * Retrieves the current temperature, in celsius degrees.
   *
   * @return a future that will contain the temperature.
   */
  CompletableFuture<Double> getCurrentTemperature();

  /**
   * Subscribes to changes in the current temperature.
   *
   * @param callback the function to call when the state changes.
   */
  void subscribeCurrentTemperature(HomekitCharacteristicChangeCallback callback);

  /** Unsubscribes from changes in the current temperature. */
  void unsubscribeCurrentTemperature();

  @Override
  default Collection<Service> getServices() {
    return Collections.singleton(new TemperatureSensorService(this));
  }
}
