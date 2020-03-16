package io.github.hapjava.accessories.optionalcharacteristic;

import io.github.hapjava.characteristics.HomekitCharacteristicChangeCallback;
import io.github.hapjava.characteristics.impl.battery.StatusLowBatteryEnum;
import java.util.concurrent.CompletableFuture;

/**
 * This characteristic describes an accessoryʼs battery status. A status of 1 indicates that the
 * battery level of the accessory is low. Value should return to 0 when the battery charges to a
 * level thats above the low threshold.
 *
 * @author Eugen Freiter
 */
public interface AccessoryWithStatusLowBattery {

  /**
   * A status of 1 indicates that the battery level of the accessory is low. Value should return to
   * 0 when the battery charges to a level thats above the low threshold.
   *
   * @return a future with the value
   */
  CompletableFuture<StatusLowBatteryEnum> getStatusLowBattery();

  /**
   * Subscribes to changes in status low battery.
   *
   * @param callback the function when the status low batter changes
   */
  void subscribeStatusLowBattery(HomekitCharacteristicChangeCallback callback);

  /** Unsubscribes from changes */
  void unsubscribeStatusLowBattery();
}
