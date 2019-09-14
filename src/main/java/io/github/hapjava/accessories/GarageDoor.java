package io.github.hapjava.accessories;

import io.github.hapjava.*;
import io.github.hapjava.accessories.properties.DoorState;
import io.github.hapjava.impl.services.GarageDoorService;
import java.util.Collection;
import java.util.Collections;
import java.util.concurrent.CompletableFuture;

/**
 * A garage door opener, with control and status of a garage door
 *
 * @author Andy Lintner
 */
public interface GarageDoor extends HomekitAccessory {

  /**
   * Retrieves the current state of the door
   *
   * @return a future which will contain the door's state
   */
  CompletableFuture<DoorState> getCurrentDoorState();

  /**
   * Retrieves the targeted state of the door
   *
   * @return a future which will contain the door's targeted state
   */
  CompletableFuture<DoorState> getTargetDoorState();

  /**
   * Retrieves an indicator of an obstruction detected by the door
   *
   * @return a future which will contain the indicator
   */
  CompletableFuture<Boolean> getObstructionDetected();

  /**
   * Sets the targeted state of the door.
   *
   * @param state the targeted state
   * @return a future that completes when the change is made
   * @throws Exception when the change cannot be made
   */
  CompletableFuture<Void> setTargetDoorState(DoorState state) throws Exception;

  /**
   * Subscribes to changes in the door's state
   *
   * @param callback the function to call when the state changes
   */
  void subscribeCurrentDoorState(HomekitCharacteristicChangeCallback callback);

  /**
   * Subscribes to changes in the door's targeted state
   *
   * @param callback the function to call when the targeted state changes
   */
  void subscribeTargetDoorState(HomekitCharacteristicChangeCallback callback);

  /**
   * Subscribes to changes in the obstruction detected indicator
   *
   * @param callback the function to call when the indicator chnages
   */
  void subscribeObstructionDetected(HomekitCharacteristicChangeCallback callback);

  /** Unsubscribes from changes in the door's state */
  void unsubscribeCurrentDoorState();

  /** Unsubscribes from changes in the door's targeted state */
  void unsubscribeTargetDoorState();

  /** Unsubscribes from changes in the door's obstruction detected indicator */
  void unsubscribeObstructionDetected();

  @Override
  default Collection<Service> getServices() {
    return Collections.singleton(new GarageDoorService(this));
  }
}
