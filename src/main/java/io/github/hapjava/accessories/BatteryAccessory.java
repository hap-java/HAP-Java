package io.github.hapjava.accessories;

import io.github.hapjava.characteristics.HomekitCharacteristicChangeCallback;
import io.github.hapjava.characteristics.impl.battery.ChargingStateEnum;
import io.github.hapjava.characteristics.impl.battery.StatusLowBatteryEnum;
import io.github.hapjava.services.Service;
import io.github.hapjava.services.impl.BatteryService;
import java.util.Collection;
import java.util.Collections;
import java.util.concurrent.CompletableFuture;

/**
 * Devices with battery .
 *
 * @author Gaston Dombiak
 */
public interface BatteryAccessory extends HomekitAccessory {

  /**
   * Retrieves the battery level of the accessory.
   *
   * @return a future that will contain the accessory's battery state
   */
  CompletableFuture<Integer> getBatteryLevel();

  /**
   * Queries if the device battery level is low; returning a value of true will cause a low-battery
   * status to appear in Home for the device.
   *
   * @return a future that will contain the accessory's low battery state
   */
  CompletableFuture<StatusLowBatteryEnum> getLowBatteryState();

  /**
   * Retriece the battery charging state.
   *
   * @return a future that will contain the battery charging state
   */
  CompletableFuture<ChargingStateEnum> getChargingState();

  /**
   * Subscribes to changes in the battery level.
   *
   * @param callback the function to call when battery level changes.
   */
  void subscribeBatteryLevel(HomekitCharacteristicChangeCallback callback);
  /**
   * Subscribes to changes in the battery level.
   *
   * @param callback the function to call when low battery state changes.
   */
  void subscribeLowBatteryState(HomekitCharacteristicChangeCallback callback);

  /**
   * Subscribes to changes in the battery level.
   *
   * @param callback the function to call when low battery state changes.
   */
  void subscribeBatteryChargingState(HomekitCharacteristicChangeCallback callback);

  /** Unsubscribes from changes in the battery level. */
  void unsubscribeBatteryLevel();

  /** Unsubscribes from changes in the low battery state. */
  void unsubscribeLowBatteryState();

  /** Unsubscribes from changes in the low battery state. */
  void unsubscribeBatteryChargingState();

  @Override
  default Collection<Service> getServices() {
    return Collections.singleton(new BatteryService(this));
  }
}
