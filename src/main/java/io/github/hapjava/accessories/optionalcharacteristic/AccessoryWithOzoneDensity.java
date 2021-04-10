package io.github.hapjava.accessories.optionalcharacteristic;

import io.github.hapjava.characteristics.HomekitCharacteristicChangeCallback;
import io.github.hapjava.characteristics.impl.airquality.OzoneDensityCharacteristic;
import java.util.concurrent.CompletableFuture;

/** Accessory with Ozone Density characteristic. */
public interface AccessoryWithOzoneDensity {

  /**
   * Retrieves the ozone density.
   *
   * @return a future with the ozone density
   */
  CompletableFuture<Double> getOzoneDensity();

  /**
   * return the min value for ozone density. overwrite if you want to change the default value.
   *
   * @return min ozone density
   */
  default double getMinOzoneDensity() {
    return OzoneDensityCharacteristic.DEFAULT_MIN_VALUE;
  }

  /**
   * return the max value for ozone density. overwrite if you want to change the default value.
   *
   * @return max ozone density
   */
  default double getMaxOzoneDensity() {
    return OzoneDensityCharacteristic.DEFAULT_MAX_VALUE;
  }

  /**
   * return the min step value for ozone density. overwrite if you want to change the default value.
   *
   * @return min step ozone density
   */
  default double getMinStepOzoneDensity() {
    return OzoneDensityCharacteristic.DEFAULT_STEP;
  }

  /**
   * Subscribes to changes in ozone density.
   *
   * @param callback the function when ozone density changes
   */
  void subscribeOzoneDensity(HomekitCharacteristicChangeCallback callback);

  /** Unsubscribes from changes */
  void unsubscribeOzoneDensity();
}
