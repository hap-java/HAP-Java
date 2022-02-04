package io.github.hapjava.accessories.optionalcharacteristic;

import io.github.hapjava.characteristics.HomekitCharacteristicChangeCallback;
import java.util.concurrent.CompletableFuture;

/**
 * Accessory with AirPlay enable characteristic {@link
 * io.github.hapjava.characteristics.impl.common.AirPlayEnableCharacteristic}.
 */
public interface AccessoryWithAirPlayEnable {

  /**
   * Retrieves the AirPlay enable state
   *
   * @return a future that will contain the AirPlay enable state.
   */
  CompletableFuture<Integer> getAirPlayEnable();

  /**
   * Sets the AirPlay enable state
   *
   * @param state AirPlay enable state
   * @return a future that completes when the AirPlay enable is changed
   * @throws Exception when the AirPlay enable cannot be set
   */
  CompletableFuture<Void> setAirPlayEnable(Integer state) throws Exception;

  /**
   * Subscribes to changes in the AirPlay enable state.
   *
   * @param callback the function to call when the AirPlay enable state changes.
   */
  void subscribeAirPlayEnable(HomekitCharacteristicChangeCallback callback);

  /** Unsubscribes from changes in the AirPlay enable state. */
  void unsubscribeAirPlayEnable();
}
