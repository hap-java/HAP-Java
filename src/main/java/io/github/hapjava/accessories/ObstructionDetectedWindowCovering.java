package io.github.hapjava.accessories;

import io.github.hapjava.HomekitCharacteristicChangeCallback;
import java.util.concurrent.CompletableFuture;

public interface ObstructionDetectedWindowCovering {

  /**
   * Retrieves an indication that the window covering is obstructed from moving
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
