package io.github.hapjava.accessories.optionalcharacteristic;

import io.github.hapjava.characteristics.HomekitCharacteristicChangeCallback;
import java.util.concurrent.CompletableFuture;

/** Accessory with hold position */
public interface AccessoryWithObstructionDetection {

  /**
   * Retrieves an indication obstructed is detected
   *
   * @return a future that will contain a boolean indicating whether an obstruction is present
   */
  CompletableFuture<Boolean> getObstructionDetected();

  /**
   * Subscribes to changes in the obstruction detected state
   *
   * @param callback the function to call when the state changes.
   */
  void subscribeObstructionDetected(HomekitCharacteristicChangeCallback callback);

  /** Unsubscribes from changes in the obstruction detected state */
  void unsubscribeObstructionDetected();
}
