package io.github.hapjava.accessories.optionalcharacteristic;

import io.github.hapjava.characteristics.HomekitCharacteristicChangeCallback;
import java.util.concurrent.CompletableFuture;

/** Accessory with cooling threshold temperature. */
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
   * return the min value for cooling threshold temperature. overwrite if you want to change the
   * default value.
   *
   * @return min threshold temperature
   */
  default double getMinCoolingThresholdTemperature() {
    return 10;
  }

  /**
   * return the max value for cooling threshold temperature. overwrite if you want to change the
   * default value.
   *
   * @return max threshold temperature
   */
  default double getMaxCoolingThresholdTemperature() {
    return 35;
  }

  /**
   * return the min step value for cooling threshold temperature. overwrite if you want to change
   * the default value.
   *
   * @return step for threshold temperature
   */
  default double getStepCoolingThresholdTemperature() {
    return 0.1;
  }

  /**
   * Subscribes to changes in the cooling threshold.
   *
   * @param callback the function to call when the state changes.
   */
  void subscribeCoolingThresholdTemperature(HomekitCharacteristicChangeCallback callback);

  /** Unsubscribes from changes in the cooling threshold. */
  void unsubscribeCoolingThresholdTemperature();
}
