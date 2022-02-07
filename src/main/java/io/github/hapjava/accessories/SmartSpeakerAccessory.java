package io.github.hapjava.accessories;

import io.github.hapjava.characteristics.HomekitCharacteristicChangeCallback;
import io.github.hapjava.characteristics.impl.television.CurrentMediaStateEnum;
import io.github.hapjava.characteristics.impl.television.TargetMediaStateEnum;
import io.github.hapjava.services.Service;
import io.github.hapjava.services.impl.SmartSpeakerService;
import java.util.Collection;
import java.util.Collections;
import java.util.concurrent.CompletableFuture;

/** Smart Speaker accessory. */
public interface SmartSpeakerAccessory extends HomekitAccessory {

  /**
   * Retrieves the current media state (see {@link
   * io.github.hapjava.characteristics.impl.television.CurrentMediaStateEnum} for supported values).
   *
   * @return a future that will contain the current media state
   */
  CompletableFuture<CurrentMediaStateEnum> getCurrentMediaState();

  /**
   * Subscribes to changes in the current media state.
   *
   * @param callback the function to call when the current media state changes.
   */
  void subscribeCurrentMediaState(HomekitCharacteristicChangeCallback callback);

  /** Unsubscribes from changes in the current media state. */
  void unsubscribeCurrentMediaState();

  /**
   * Retrieves the target media state (see {@link TargetMediaStateEnum} for supported values).
   *
   * @return a future that will contain the target media state
   */
  CompletableFuture<TargetMediaStateEnum> getTargetMediaState();

  /**
   * Set the target media state (see {@link TargetMediaStateEnum} for supported values).
   *
   * @param targetMediaState target media state
   * @return a future that completes when the change is made
   */
  CompletableFuture<Void> setTargetMediaState(TargetMediaStateEnum targetMediaState);

  /**
   * Subscribes to changes in the target media state.
   *
   * @param callback the function to call when the target media state changes.
   */
  void subscribeTargetMediaState(HomekitCharacteristicChangeCallback callback);

  /** Unsubscribes from changes in the target media state. */
  void unsubscribeTargetMediaState();

  @Override
  default Collection<Service> getServices() {
    return Collections.singleton(new SmartSpeakerService(this));
  }
}
