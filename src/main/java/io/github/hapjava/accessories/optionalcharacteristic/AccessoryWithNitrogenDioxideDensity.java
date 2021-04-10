package io.github.hapjava.accessories.optionalcharacteristic;

import io.github.hapjava.characteristics.HomekitCharacteristicChangeCallback;
import io.github.hapjava.characteristics.impl.airquality.NitrogenDioxideDensityCharacteristic;
import java.util.concurrent.CompletableFuture;

/** Accessory with nitrogen dioxide density characteristic. */
public interface AccessoryWithNitrogenDioxideDensity {

  /**
   * Retrieves the nitrogen dioxide density.
   *
   * @return a future with the nitrogen dioxide density
   */
  CompletableFuture<Double> getNitrogenDioxideDensity();

  /**
   * return the min value for nitrogen dioxide density. overwrite if you want to change the default
   * value.
   *
   * @return min nitrogen dioxide density
   */
  default double getMinNitrogenDioxideDensity() {
    return NitrogenDioxideDensityCharacteristic.DEFAULT_MIN_VALUE;
  }

  /**
   * return the max value for nitrogen dioxide density. overwrite if you want to change the default
   * value.
   *
   * @return max nitrogen dioxide density
   */
  default double getMaxNitrogenDioxideDensity() {
    return NitrogenDioxideDensityCharacteristic.DEFAULT_MAX_VALUE;
  }

  /**
   * return the min step value for nitrogen dioxide density. overwrite if you want to change the
   * default value.
   *
   * @return min step nitrogen dioxide density
   */
  default double getMinStepNitrogenDioxideDensity() {
    return NitrogenDioxideDensityCharacteristic.DEFAULT_STEP;
  }

  /**
   * Subscribes to changes in nitrogen dioxide density.
   *
   * @param callback the function when nitrogen dioxide density changes
   */
  void subscribeNitrogenDioxideDensity(HomekitCharacteristicChangeCallback callback);

  /** Unsubscribes from changes */
  void unsubscribeNitrogenDioxideDensity();
}
