package io.github.hapjava.accessories.optionalcharacteristic;

import io.github.hapjava.characteristics.HomekitCharacteristicChangeCallback;
import java.util.concurrent.CompletableFuture;

/** Accessory with PM10 Density characteristic. */
public interface AccessoryWithPM10Density {

  /**
   * Retrieves the PM10 density.
   *
   * @return a future with the PM10 density
   */
  CompletableFuture<Double> getPM10Density();

  /**
   * Subscribes to changes in PM10 density.
   *
   * @param callback the function when PM10 density changes
   */
  void subscribePM10Density(HomekitCharacteristicChangeCallback callback);

  /** Unsubscribes from changes */
  void unsubscribePM10Density();
}
