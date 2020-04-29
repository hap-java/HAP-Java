package io.github.hapjava.accessories.optionalcharacteristic;

import io.github.hapjava.characteristics.HomekitCharacteristicChangeCallback;
import java.util.concurrent.CompletableFuture;

/**
 * Accessory with volume values.
 *
 * @author Eugen Freiter
 */
public interface AccessoryWithVolume {

  /**
   * Retrieves the current volume
   *
   * @return a future that will contain the volume, expressed as an integer between 0 and 100.
   */
  CompletableFuture<Integer> getVolume();

  /**
   * Sets the current volume
   *
   * @param value the volume, on a scale of 0 to 100, to set
   * @return a future that completes when the volume is changed
   * @throws Exception when the volume cannot be set
   */
  CompletableFuture<Void> setVolume(Integer value) throws Exception;

  /**
   * Subscribes to changes in the volume.
   *
   * @param callback the function to call when the state changes.
   */
  void subscribeVolume(HomekitCharacteristicChangeCallback callback);

  /** Unsubscribes from changes in the volume. */
  void unsubscribeVolume();
}
