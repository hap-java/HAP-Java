package io.github.hapjava.accessories.optionalcharacteristic;

import io.github.hapjava.characteristics.HomekitCharacteristicChangeCallback;
import io.github.hapjava.characteristics.impl.airquality.VOCDensityCharacteristic;
import java.util.concurrent.CompletableFuture;

/** Accessory with VOC Density characteristic. */
public interface AccessoryWithVOCDensity {

  /**
   * Retrieves the VOC density.
   *
   * @return a future with the VOC density
   */
  CompletableFuture<Double> getVOCDensity();

  /**
   * return the min value for VOC density. overwrite if you want to change the default value.
   *
   * @return min VOC density
   */
  default double getMinVOCDensity() {
    return VOCDensityCharacteristic.DEFAULT_MIN_VALUE;
  }

  /**
   * return the max value for VOC density. overwrite if you want to change the default value.
   *
   * @return max VOC density
   */
  default double getMaxVOCDensity() {
    return VOCDensityCharacteristic.DEFAULT_MAX_VALUE;
  }

  /**
   * return the min step value for VOC density. overwrite if you want to change the default value.
   *
   * @return min step VOC density
   */
  default double getMinStepVOCDensity() {
    return VOCDensityCharacteristic.DEFAULT_STEP;
  }

  /**
   * Subscribes to changes in VOC density.
   *
   * @param callback the function when VOC density changes
   */
  void subscribeVOCDensity(HomekitCharacteristicChangeCallback callback);

  /** Unsubscribes from changes */
  void unsubscribeVOCDensity();
}
