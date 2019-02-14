package com.beowulfe.hap.accessories.characteristics;

import com.beowulfe.hap.HomekitCharacteristicChangeCallback;
import java.util.concurrent.CompletableFuture;

public interface LowBatteryStatus {
  /**
   * Queries if the device battery level is low; returning a value of true will cause a low-battery
   * status to appear in Home for the device.
   *
   * @return a future that will contain the accessory's low battery state
   */
  CompletableFuture<Boolean> getLowBatteryState();

  /**
   * Subscribes to changes in the battery level.
   *
   * @param callback the function to call when low battery state changes.
   */
  void subscribeLowBatteryState(HomekitCharacteristicChangeCallback callback);

  /** Unsubscribes from changes in the low battery state. */
  void unsubscribeLowBatteryState();
}
