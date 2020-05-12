package io.github.hapjava.accessories.optionalcharacteristic;

import io.github.hapjava.characteristics.HomekitCharacteristicChangeCallback;
import java.util.concurrent.CompletableFuture;

/**
 * Humidifier with humidity threshold.
 *
 * @author Eugen Freiter
 */
public interface AccessoryWithHumidityHumidifierThreshold {

  /**
   * Retrieves the humidity threshold.
   *
   * @return a future that will contain the humidity threshold.
   */
  CompletableFuture<Double> getHumidityThreshold();

  /**
   * Sets the humidity threshold above which the humidifier should be turned on.
   *
   * @param value the humidity threshold, in celsius degrees.
   * @throws Exception when the threshold cannot be changed.
   */
  void setHumidityThreshold(Double value) throws Exception;

  /**
   * Subscribes to changes in the humidity threshold.
   *
   * @param callback the function to call when the state changes.
   */
  void subscribeHumidityThreshold(HomekitCharacteristicChangeCallback callback);

  /** Unsubscribes from changes in the humidity threshold. */
  void unsubscribeHumidityThreshold();
}
