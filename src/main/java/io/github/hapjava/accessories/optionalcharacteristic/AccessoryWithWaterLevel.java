package io.github.hapjava.accessories.optionalcharacteristic;

import io.github.hapjava.characteristics.HomekitCharacteristicChangeCallback;
import java.util.concurrent.CompletableFuture;

/** Accessory with water level. */
public interface AccessoryWithWaterLevel {

  /**
   * Retrieves the water level in percent.
   *
   * @return a future that will contain the water level, expressed as an double between 0 and 100.
   */
  CompletableFuture<Double> getWaterLevel();

  /**
   * Subscribes to changes in the water level.
   *
   * @param callback the function to call when the state changes.
   */
  void subscribeWaterLevel(HomekitCharacteristicChangeCallback callback);

  /** Unsubscribes from changes in the water level. */
  void unsubscribeWaterLevel();
}
