package io.github.hapjava.accessories.optionalcharacteristic;

import io.github.hapjava.characteristics.HomekitCharacteristicChangeCallback;
import io.github.hapjava.characteristics.impl.fan.RotationDirectionEnum;
import java.util.concurrent.CompletableFuture;

/** accessory with rotation direction characteristics. */
public interface AccessoryWithRotationDirection {

  /**
   * Retrieves the current rotation direction.
   *
   * @return a future that will contain the direction
   */
  CompletableFuture<RotationDirectionEnum> getRotationDirection();

  /**
   * Sets the rotation direction
   *
   * @param direction the direction to set
   * @return a future that completes when the change is made
   * @throws Exception when the change cannot be made
   */
  CompletableFuture<Void> setRotationDirection(RotationDirectionEnum direction) throws Exception;

  /**
   * Subscribes to changes in the rotation direction.
   *
   * @param callback the function to call when the direction changes.
   */
  void subscribeRotationDirection(HomekitCharacteristicChangeCallback callback);

  /** Unsubscribes from changes in the rotation direction. */
  void unsubscribeRotationDirection();
}
