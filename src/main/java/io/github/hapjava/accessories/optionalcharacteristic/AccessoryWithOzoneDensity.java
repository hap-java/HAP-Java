package io.github.hapjava.accessories.optionalcharacteristic;

import io.github.hapjava.characteristics.HomekitCharacteristicChangeCallback;
import java.util.concurrent.CompletableFuture;

/**
 * Accessory with Ozone Density characteristic.
 *
 * @author Eugen Freiter
 */
public interface AccessoryWithOzoneDensity {

  /**
   * Retrieves the ozone density.
   *
   * @return a future with the ozone density
   */
  CompletableFuture<Double> getOzoneDensity();

  /**
   * Subscribes to changes in ozone density.
   *
   * @param callback the function when ozone density changes
   */
  void subscribeOzoneDensity(HomekitCharacteristicChangeCallback callback);

  /** Unsubscribes from changes */
  void unsubscribeOzoneDensity();
}
