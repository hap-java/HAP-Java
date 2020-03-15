package io.github.hapjava.accessories.optionalcharacteristic;

import io.github.hapjava.characteristics.HomekitCharacteristicChangeCallback;
import java.util.concurrent.CompletableFuture;

/**
 * Accessory with carbon dioxide level and peak level characteristic.
 *
 * @author Eugen Freiter
 */
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
