package io.github.hapjava.accessories.optionalcharacteristic;

import io.github.hapjava.characteristics.HomekitCharacteristicChangeCallback;
import io.github.hapjava.characteristics.impl.airquality.PM25DensityCharacteristic;
import java.util.concurrent.CompletableFuture;

/** Accessory with PM25 Density characteristic. */
public interface AccessoryWithPM25Density {

  /**
   * Retrieves the PM25 density.
   *
   * @return a future with the PM25 density
   */
  CompletableFuture<Double> getPM25Density();

  /**
   * return the min value for PM25 density. overwrite if you want to change the default value.
   *
   * @return min PM25 density
   */
  default double getMinPM25Density() {
    return PM25DensityCharacteristic.DEFAULT_MIN_VALUE;
  }

  /**
   * return the max value for PM25 density. overwrite if you want to change the default value.
   *
   * @return max PM25 density
   */
  default double getMaxPM25Density() {
    return PM25DensityCharacteristic.DEFAULT_MAX_VALUE;
  }

  /**
   * return the min step value for PM25 density. overwrite if you want to change the default value.
   *
   * @return min step PM25 density
   */
  default double getMinStepPM25Density() {
    return PM25DensityCharacteristic.DEFAULT_STEP;
  }

  /**
   * Subscribes to changes in PM25 density.
   *
   * @param callback the function when PM25 density changes
   */
  void subscribePM25Density(HomekitCharacteristicChangeCallback callback);

  /** Unsubscribes from changes */
  void unsubscribePM25Density();
}
