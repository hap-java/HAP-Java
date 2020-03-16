package io.github.hapjava.accessories.optionalcharacteristic;

import io.github.hapjava.characteristics.HomekitCharacteristicChangeCallback;
import java.util.concurrent.CompletableFuture;

/**
 * Accessory with cooling threshold temperature.
 *
 * @author Eugen Freiter
 */
public interface AccessoryWithCoolingThresholdTemperature {

  /**
   * Retrieves the temperature above which the thermostat should begin cooling.
   *
   * @return a future that will contain the threshold temperature, in celsius degrees.
   */
  CompletableFuture<Double> getCoolingThresholdTemperature();

  /**
   * Sets the temperature above which the thermostat should begin cooling.
   *
   * @param value the threshold temperature, in celsius degrees.
   * @throws Exception when the threshold temperature cannot be changed.
   */
  void setCoolingThresholdTemperature(Double value) throws Exception;

  /**
   * Subscribes to changes in the cooling threshold.
   *
   * @param callback the function to call when the state changes.
   */
  void subscribeCoolingThresholdTemperature(HomekitCharacteristicChangeCallback callback);

  /** Unsubscribes from changes in the cooling threshold. */
  void unsubscribeCoolingThresholdTemperature();
}
