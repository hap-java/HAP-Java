package io.github.hapjava.accessories.optionalcharacteristic;

import io.github.hapjava.characteristics.HomekitCharacteristicChangeCallback;
import java.util.concurrent.CompletableFuture;

/**
 * Accessory with color temperature.
 *
 * @author Eugen Freiter
 */
public interface AccessoryWithColorTemperature {

  /**
   * Retrieves the color temperature
   *
   * @return a future that will contain the brightness, expressed as an integer between 0 and 100.
   */
  CompletableFuture<Integer> getColorTemperature();

  /**
   * Sets the color temperature
   *
   * @param value the brightness, on a scale of 0 to 100, to set
   * @return a future that completes when the brightness is changed
   * @throws Exception when the brightness cannot be set
   */
  CompletableFuture<Void> setColorTemperature(Integer value) throws Exception;

  /**
   * Subscribes to changes in color temperature.
   *
   * @param callback the function to call when the color temperature changes.
   */
  void subscribeColorTemperature(HomekitCharacteristicChangeCallback callback);

  /** Unsubscribes from changes in the color temperature. */
  void unsubscribeColorTemperature();
}
