package io.github.hapjava.accessories.optionalcharacteristic;

import io.github.hapjava.characteristics.HomekitCharacteristicChangeCallback;
import java.util.concurrent.CompletableFuture;

/**
 * Accessory with carbon dioxide level and peak level characteristic.
 *
 * @author Eugen Freiter
 */
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
