package io.github.hapjava.accessories.optionalcharacteristic;

import io.github.hapjava.characteristics.HomekitCharacteristicChangeCallback;
import io.github.hapjava.characteristics.impl.airquality.SulphurDioxideDensityCharacteristic;
import java.util.concurrent.CompletableFuture;

/** Accessory with sulphur dioxide density characteristic. */
public interface AccessoryWithSulphurDioxideDensity {

  /**
   * Retrieves the sulphur dioxide density.
   *
   * @return a future with the sulphur dioxide density
   */
  CompletableFuture<Double> getSulphurDioxideDensity();

  /**
   * return the min value for sulphur dioxide density. overwrite if you want to change the default
   * value.
   *
   * @return min sulphur dioxide density
   */
  default double getMinSulphurDioxideDensity() {
    return SulphurDioxideDensityCharacteristic.DEFAULT_MIN_VALUE;
  }

  /**
   * return the max value for sulphur dioxide density. overwrite if you want to change the default
   * value.
   *
   * @return max sulphur dioxide density
   */
  default double getMaxSulphurDioxideDensity() {
    return SulphurDioxideDensityCharacteristic.DEFAULT_MAX_VALUE;
  }

  /**
   * return the min step value for sulphur dioxide density. overwrite if you want to change the
   * default value.
   *
   * @return min step sulphur dioxide density
   */
  default double getMinStepSulphurDioxideDensity() {
    return SulphurDioxideDensityCharacteristic.DEFAULT_STEP;
  }

  /**
   * Subscribes to changes in sulphur dioxide density.
   *
   * @param callback the function when sulphur dioxide density changes
   */
  void subscribeSulphurDioxideDensity(HomekitCharacteristicChangeCallback callback);

  /** Unsubscribes from changes */
  void unsubscribeSulphurDioxideDensity();
}
