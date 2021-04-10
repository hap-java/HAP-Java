package io.github.hapjava.accessories.optionalcharacteristic;

import io.github.hapjava.characteristics.HomekitCharacteristicChangeCallback;
import io.github.hapjava.characteristics.impl.carbonmonoxidesensor.CarbonMonoxideLevelCharacteristic;
import io.github.hapjava.characteristics.impl.carbonmonoxidesensor.CarbonMonoxidePeakLevelCharacteristic;
import java.util.concurrent.CompletableFuture;

/** Accessory with carbon monoxide level and peak level characteristic. */
public interface AccessoryWithCarbonMonoxideLevel {

  /**
   * Retrieves the carbon monoxide peak level
   *
   * @return a future that will contain the carbon monoxide level as a value between 0 and 100000
   */
  CompletableFuture<Double> getCarbonMonoxidePeakLevel();

  /**
   * Retrieves the carbon monoxide level
   *
   * @return a future that will contain the carbon monoxide level as a value between 0 and 100000
   */
  CompletableFuture<Double> getCarbonMonoxideLevel();

  /**
   * return the min value for carbon monoxide level. overwrite if you want to change the default
   * value.
   *
   * @return min carbon monoxide level
   */
  default double getMinCarbonMonoxideLevel() {
    return CarbonMonoxideLevelCharacteristic.DEFAULT_MIN_VALUE;
  }

  /**
   * return the max value for carbon monoxide level. overwrite if you want to change the default
   * value.
   *
   * @return max carbon monoxide level
   */
  default double getMaxCarbonMonoxideLevel() {
    return CarbonMonoxideLevelCharacteristic.DEFAULT_MAX_VALUE;
  }

  /**
   * return the min step value for carbon monoxide level. overwrite if you want to change the
   * default value.
   *
   * @return min step carbon monoxide level
   */
  default double getMinStepCarbonMonoxideLevel() {
    return CarbonMonoxideLevelCharacteristic.DEFAULT_STEP;
  }

  /**
   * return the min value for carbon monoxide peak level. overwrite if you want to change the
   * default value.
   *
   * @return min carbon monoxide peak level
   */
  default double getMinCarbonMonoxidePeakLevel() {
    return CarbonMonoxidePeakLevelCharacteristic.DEFAULT_MIN_VALUE;
  }

  /**
   * return the max value for carbon monoxide peak level. overwrite if you want to change the
   * default value.
   *
   * @return max carbon monoxide peak level
   */
  default double getMaxCarbonMonoxidePeakLevel() {
    return CarbonMonoxidePeakLevelCharacteristic.DEFAULT_MAX_VALUE;
  }

  /**
   * return the min step value for carbon monoxide peak level. overwrite if you want to change the
   * default value.
   *
   * @return min step carbon monoxide peak level
   */
  default double getMinStepCarbonMonoxidePeakLevel() {
    return CarbonMonoxidePeakLevelCharacteristic.DEFAULT_STEP;
  }

  /**
   * Subscribes to changes in the carbon monoxide level.
   *
   * @param callback the function to call when the state changes.
   */
  void subscribeCarbonMonoxideLevel(HomekitCharacteristicChangeCallback callback);

  /** Unsubscribes from changes in the carbon monoxide level. */
  void unsubscribeCarbonMonoxideLevel();

  /**
   * Subscribes to changes in the carbon monoxide level.
   *
   * @param callback the function to call when the state changes.
   */
  void subscribeCarbonMonoxidePeakLevel(HomekitCharacteristicChangeCallback callback);

  /** Unsubscribes from changes in the carbon monoxide level. */
  void unsubscribeCarbonMonoxidePeakLevel();
}
