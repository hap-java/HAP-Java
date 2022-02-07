package io.github.hapjava.accessories.optionalcharacteristic;

import io.github.hapjava.characteristics.HomekitCharacteristicChangeCallback;
import io.github.hapjava.characteristics.impl.television.TargetMediaStateEnum;
import java.util.concurrent.CompletableFuture;

/**
 * Accessory with target media state characteristic {@link
 * io.github.hapjava.characteristics.impl.television.TargetMediaStateCharacteristic}.
 */
public interface AccessoryWithTargetMediaState {

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
}
