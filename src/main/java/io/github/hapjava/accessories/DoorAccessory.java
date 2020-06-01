package io.github.hapjava.accessories;

import io.github.hapjava.characteristics.HomekitCharacteristicChangeCallback;
import io.github.hapjava.characteristics.impl.windowcovering.PositionStateEnum;
import io.github.hapjava.services.Service;
import io.github.hapjava.services.impl.DoorService;
import java.util.Collection;
import java.util.Collections;
import java.util.concurrent.CompletableFuture;

/** A motorized door with current and target position. */
public interface DoorAccessory extends HomekitAccessory {

  /**
   * Retrieves the position state of the door
   *
   * @return a future which will contain the door's position state
   */
  CompletableFuture<PositionStateEnum> getPositionState();

  /**
   * Retrieves the current position of the door
   *
   * @return a future which will contain the door's current position
   */
  CompletableFuture<Integer> getCurrentPosition();

  /**
   * Retrieves the target position of the door
   *
   * @return a future which will contain the door's target position
   */
  CompletableFuture<Integer> getTargetPosition();

  /**
   * Sets the targeted state of the door.
   *
   * @param position the targeted position
   * @return a future that completes when the change is made
   * @throws Exception when the change cannot be made
   */
  CompletableFuture<Void> setTargetPosition(Integer position) throws Exception;

  /**
   * Subscribes to changes in the door's position state
   *
   * @param callback the function to call when the state changes
   */
  void subscribePositionState(HomekitCharacteristicChangeCallback callback);

  /**
   * Subscribes to changes in the door's current position
   *
   * @param callback the function to call when the current position changes
   */
  void subscribeCurrentPosition(HomekitCharacteristicChangeCallback callback);

  /**
   * Subscribes to changes in the door's target position
   *
   * @param callback the function to call when the target position changes
   */
  void subscribeTargetPosition(HomekitCharacteristicChangeCallback callback);

  /** Unsubscribes from changes in the door's state */
  void unsubscribePositionState();

  /** Unsubscribes from changes in the door's current position */
  void unsubscribeCurrentPosition();

  /** Unsubscribes from changes in the door's target position */
  void unsubscribeTargetPosition();

  @Override
  default Collection<Service> getServices() {
    return Collections.singleton(new DoorService(this));
  }
}
