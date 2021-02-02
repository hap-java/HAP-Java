package io.github.hapjava.accessories.optionalcharacteristic;

import io.github.hapjava.characteristics.HomekitCharacteristicChangeCallback;
import io.github.hapjava.characteristics.impl.carbondioxidesensor.CarbonDioxideLevelCharacteristic;
import io.github.hapjava.characteristics.impl.carbondioxidesensor.CarbonDioxidePeakLevelCharacteristic;
import java.util.concurrent.CompletableFuture;

/** Accessory with carbon dioxide level and peak level characteristic. */
public interface AccessoryWithCarbonDioxideLevel {

  /**
   * Retrieves the carbon dioxide peak level
   *
   * @return a future that will contain the carbon dioxide level as a value between 0 and 100000
   */
  CompletableFuture<Double> getCarbonDioxidePeakLevel();

  /**
   * Retrieves the carbon dioxide level
   *
   * @return a future that will contain the carbon dioxide level as a value between 0 and 100000
   */
  CompletableFuture<Double> getCarbonDioxideLevel();

  /**
   * return the min value for carbon dioxide level. overwrite if you want to change the default
   * value.
   *
   * @return min carbon dioxide level
   */
  default double getMinCarbonDioxideLevel() {
    return CarbonDioxideLevelCharacteristic.DEFAULT_MIN_VALUE;
  }

  /**
   * return the max value for carbon dioxide level. overwrite if you want to change the default
   * value.
   *
   * @return max carbon dioxide level
   */
  default double getMaxCarbonDioxideLevel() {
    return CarbonDioxideLevelCharacteristic.DEFAULT_MAX_VALUE;
  }

  /**
   * return the min step value for carbon dioxide level. overwrite if you want to change the default
   * value.
   *
   * @return min step carbon dioxide level
   */
  default double getMinStepCarbonDioxideLevel() {
    return CarbonDioxideLevelCharacteristic.DEFAULT_STEP;
  }

  /**
   * return the min value for carbon dioxide peak level. overwrite if you want to change the default
   * value.
   *
   * @return min carbon dioxide peak level
   */
  default double getMinCarbonDioxidePeakLevel() {
    return CarbonDioxidePeakLevelCharacteristic.DEFAULT_MIN_VALUE;
  }

  /**
   * return the max value for carbon dioxide peak level. overwrite if you want to change the default
   * value.
   *
   * @return max carbon dioxide peak level
   */
  default double getMaxCarbonDioxidePeakLevel() {
    return CarbonDioxidePeakLevelCharacteristic.DEFAULT_MAX_VALUE;
  }

  /**
   * return the min step value for carbon dioxide peak level. overwrite if you want to change the
   * default value.
   *
   * @return min step carbon dioxide peak level
   */
  default double getMinStepCarbonDioxidePeakLevel() {
    return CarbonDioxidePeakLevelCharacteristic.DEFAULT_STEP;
  }

  /**
   * Subscribes to changes in the carbon dioxide level.
   *
   * @param callback the function to call when the state changes.
   */
  void subscribeCarbonDioxideLevel(HomekitCharacteristicChangeCallback callback);

  /** Unsubscribes from changes in the carbon dioxide level. */
  void unsubscribeCarbonDioxideLevel();

  /**
   * Subscribes to changes in the carbon dioxide level.
   *
   * @param callback the function to call when the state changes.
   */
  void subscribeCarbonDioxidePeakLevel(HomekitCharacteristicChangeCallback callback);

  /** Unsubscribes from changes in the carbon dioxide level. */
  void unsubscribeCarbonDioxidePeakLevel();
}
