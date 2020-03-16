package io.github.hapjava.accessories.optionalcharacteristic;

import io.github.hapjava.characteristics.HomekitCharacteristicChangeCallback;
import java.util.concurrent.CompletableFuture;

/**
 * accessory with rotation speed characteristics.
 *
 * @author Eugen Freiter
 */
public interface AccessoryWithRotationSpeed {

  /**
   * Retrieves the current speed of the rotation
   *
   * @return a future that will contain the speed, expressed as an integer between 0 and 100.
   */
  CompletableFuture<Integer> getRotationSpeed();

  /**
   * Sets the speed of the rotation
   *
   * @param speed the speed to set, expressed as an integer between 0 and 100.
   * @return a future that completes when the change is made
   * @throws Exception when the change cannot be made
   */
  CompletableFuture<Void> setRotationSpeed(Integer speed) throws Exception;

  /**
   * Subscribes to changes in the rotation speed.
   *
   * @param callback the function to call when the speed changes.
   */
  void subscribeRotationSpeed(HomekitCharacteristicChangeCallback callback);

  /** Unsubscribes from changes in the rotation speed. */
  void unsubscribeRotationSpeed();
}
