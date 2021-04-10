package io.github.hapjava.accessories.optionalcharacteristic;

import io.github.hapjava.characteristics.HomekitCharacteristicChangeCallback;
import io.github.hapjava.characteristics.impl.airquality.PM10DensityCharacteristic;
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
   * return the min value for PM10 density. overwrite if you want to change the default value.
   *
   * @return min PM10 density
   */
  default double getMinPM10Density() {
    return PM10DensityCharacteristic.DEFAULT_MIN_VALUE;
  }

  /**
   * return the max value for PM10 density. overwrite if you want to change the default value.
   *
   * @return max PM10 density
   */
  default double getMaxPM10Density() {
    return PM10DensityCharacteristic.DEFAULT_MAX_VALUE;
  }

  /**
   * return the min step value for PM10 density. overwrite if you want to change the default value.
   *
   * @return min step PM10 density
   */
  default double getMinStepPM10Density() {
    return PM10DensityCharacteristic.DEFAULT_STEP;
  }

  /**
   * Subscribes to changes in PM10 density.
   *
   * @param callback the function when PM10 density changes
   */
  void subscribePM10Density(HomekitCharacteristicChangeCallback callback);

  /** Unsubscribes from changes */
  void unsubscribePM10Density();
}
