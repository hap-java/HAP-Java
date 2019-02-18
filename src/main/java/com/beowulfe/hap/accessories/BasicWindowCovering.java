package com.beowulfe.hap.accessories;

import com.beowulfe.hap.HomekitAccessory;
import com.beowulfe.hap.HomekitCharacteristicChangeCallback;
import com.beowulfe.hap.Service;
import com.beowulfe.hap.accessories.properties.WindowCoveringPositionState;
import com.beowulfe.hap.impl.services.WindowCoveringService;
import java.util.Collection;
import java.util.Collections;
import java.util.concurrent.CompletableFuture;

public interface BasicWindowCovering extends HomekitAccessory {

  /**
   * Retrieves the current position
   *
   * @return a future that will contain the position as a value between 0 and 100
   */
  CompletableFuture<Integer> getCurrentPosition();

  /**
   * Retrieves the target position
   *
   * @return a future that will contain the target position as a value between 0 and 100
   */
  CompletableFuture<Integer> getTargetPosition();

  /**
   * Retrieves the state of the position: increasing, decreasing, or stopped
   *
   * @return a future that will contain the current state
   */
  CompletableFuture<WindowCoveringPositionState> getPositionState();

  /**
   * Sets the target position
   *
   * @param position the target position to set, as a value between 1 and 100
   * @return a future that completes when the change is made
   * @throws Exception when the change cannot be made
   */
  CompletableFuture<Void> setTargetPosition(int position) throws Exception;

  /**
   * Subscribes to changes in the current position.
   *
   * @param callback the function to call when the state changes.
   */
  void subscribeCurrentPosition(HomekitCharacteristicChangeCallback callback);

  /**
   * Subscribes to changes in the target position.
   *
   * @param callback the function to call when the state changes.
   */
  void subscribeTargetPosition(HomekitCharacteristicChangeCallback callback);

  /**
   * Subscribes to changes in the position state: increasing, decreasing, or stopped
   *
   * @param callback the function to call when the state changes.
   */
  void subscribePositionState(HomekitCharacteristicChangeCallback callback);

  /** Unsubscribes from changes in the current position. */
  void unsubscribeCurrentPosition();

  /** Unsubscribes from changes in the target position. */
  void unsubscribeTargetPosition();

  /** Unsubscribes from changes in the position state */
  void unsubscribePositionState();

  @Override
  default Collection<Service> getServices() {
    return Collections.singleton(new WindowCoveringService(this));
  }
}
