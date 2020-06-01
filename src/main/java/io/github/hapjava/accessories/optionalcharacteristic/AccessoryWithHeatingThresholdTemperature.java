package io.github.hapjava.accessories.optionalcharacteristic;

import io.github.hapjava.characteristics.HomekitCharacteristicChangeCallback;
import java.util.concurrent.CompletableFuture;

public interface AccessoryWithHeatingThresholdTemperature {

  /**
   * Retrieves the temperature below which the thermostat should begin heating.
   *
   * @return a future that will contain the threshold temperature, in celsius degrees.
   */
  CompletableFuture<Double> getHeatingThresholdTemperature();

  /**
   * Sets the temperature below which the thermostat should begin heating.
   *
   * @param value the threshold temperature, in celsius degrees.
   * @throws Exception when the threshold temperature cannot be changed.
   */
  void setHeatingThresholdTemperature(Double value) throws Exception;

  /**
   * return the min value for heating threshold temperature. overwrite if you want to change the
   * default value.
   *
   * @return min threshold temperature
   */
  default double getMinHeatingThresholdTemperature() {
    return 0;
  }

  /**
   * return the max value for heating threshold temperature. overwrite if you want to change the
   * default value.
   *
   * @return max threshold temperature
   */
  default double getMaxHeatingThresholdTemperature() {
    return 25;
  }

  /**
   * return the min step value for heating threshold temperature. overwrite if you want to change
   * the default value.
   *
   * @return step for threshold temperature
   */
  default double getStepHeatingThresholdTemperature() {
    return 0.1;
  }

  /**
   * Subscribes to changes in the heating threshold.
   *
   * @param callback the function to call when the state changes.
   */
  void subscribeHeatingThresholdTemperature(HomekitCharacteristicChangeCallback callback);

  /** Unsubscribes from changes in the heating threshold. */
  void unsubscribeHeatingThresholdTemperature();
}
