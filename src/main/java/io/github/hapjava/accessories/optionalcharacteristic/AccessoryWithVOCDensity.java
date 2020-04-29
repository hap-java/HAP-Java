package io.github.hapjava.accessories.optionalcharacteristic;

import io.github.hapjava.characteristics.HomekitCharacteristicChangeCallback;
import java.util.concurrent.CompletableFuture;

/**
 * Accessory with VOC Density characteristic.
 *
 * @author Eugen Freiter
 */
public interface AccessoryWithVOCDensity {

  /**
   * Retrieves the VOC density.
   *
   * @return a future with the VOC density
   */
  CompletableFuture<Double> getVOCDensity();

  /**
   * Subscribes to changes in VOC density.
   *
   * @param callback the function when VOC density changes
   */
  void subscribeVOCDensity(HomekitCharacteristicChangeCallback callback);

  /** Unsubscribes from changes */
  void unsubscribeVOCDensity();
}
