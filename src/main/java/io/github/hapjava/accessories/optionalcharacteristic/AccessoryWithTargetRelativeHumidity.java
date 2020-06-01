package io.github.hapjava.accessories.optionalcharacteristic;

import io.github.hapjava.characteristics.HomekitCharacteristicChangeCallback;
import java.util.concurrent.CompletableFuture;

/** accessory with target relative humidity. */
public interface AccessoryWithTargetRelativeHumidity {

  /**
   * Retrieves the target relative humidity.
   *
   * @return a future that will contain the target relative humidity.
   */
  CompletableFuture<Double> getTargetRelativeHumidity();

  /**
   * Sets the target relative humidity.
   *
   * @param value the target relative humidity.
   * @throws Exception when the target relative humidity cannot be changed.
   */
  void setTargetRelativeHumidity(Double value) throws Exception;

  /**
   * Subscribes to changes in the target relative humidity.
   *
   * @param callback the function to call when the target relative humidity changes.
   */
  void subscribeTargetRelativeHumidity(HomekitCharacteristicChangeCallback callback);

  /** Unsubscribes from changes in the target relative humidity. */
  void unsubscribeTargetRelativeHumidity();
}
