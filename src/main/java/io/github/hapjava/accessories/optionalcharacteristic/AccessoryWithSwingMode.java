package io.github.hapjava.accessories.optionalcharacteristic;

import io.github.hapjava.characteristics.HomekitCharacteristicChangeCallback;
import io.github.hapjava.characteristics.impl.fan.SwingModeEnum;
import java.util.concurrent.CompletableFuture;

/** Accessory with Swing Mode characteristics. */
public interface AccessoryWithSwingMode {

  /**
   * Retrieves the swing mode.
   *
   * @return a future that will contain the swing mode
   */
  CompletableFuture<SwingModeEnum> getSwingMode();

  /**
   * Set the swing mode (DISABLED, ENABLED).
   *
   * @param swingMode swing mode
   * @return a future that completes when the change is made
   */
  CompletableFuture<Void> setSwingMode(SwingModeEnum swingMode);

  /**
   * Subscribes to changes in the swing mode.
   *
   * @param callback the function to call when the swing mode changes.
   */
  void subscribeSwingMode(HomekitCharacteristicChangeCallback callback);

  /** Unsubscribes from changes in the swing mode. */
  void unsubscribeSwingMode();
}
