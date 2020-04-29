package io.github.hapjava.accessories.optionalcharacteristic;

import io.github.hapjava.characteristics.HomekitCharacteristicChangeCallback;
import java.util.concurrent.CompletableFuture;

/**
 * Accessory with PM25 Density characteristic.
 *
 * @author Eugen Freiter
 */
public interface AccessoryWithPM25Density {

  /**
   * Retrieves the PM25 density.
   *
   * @return a future with the PM25 density
   */
  CompletableFuture<Double> getPM25Density();

  /**
   * Subscribes to changes in PM25 density.
   *
   * @param callback the function when PM25 density changes
   */
  void subscribePM25Density(HomekitCharacteristicChangeCallback callback);

  /** Unsubscribes from changes */
  void unsubscribePM25Density();
}
