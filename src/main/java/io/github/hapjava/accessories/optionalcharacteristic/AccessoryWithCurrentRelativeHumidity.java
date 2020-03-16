package io.github.hapjava.accessories.optionalcharacteristic;

import io.github.hapjava.characteristics.HomekitCharacteristicChangeCallback;
import java.util.concurrent.CompletableFuture;

/** accessory with current relative humidity. */
public interface AccessoryWithCurrentRelativeHumidity {

  /**
   * Retrieves the current relative humidity.
   *
   * @return a future that will contain the current relative humidity.
   */
  CompletableFuture<Double> getCurrentRelativeHumidity();

  /**
   * Subscribes to changes in current relative humidity.
   *
   * @param callback the function to call when the current relative humidity changes.
   */
  void subscribeCurrentRelativeHumidity(HomekitCharacteristicChangeCallback callback);

  /** Unsubscribes from changes in the current relative humidity. */
  void unsubscribeCurrentRelativeHumidity();
}
