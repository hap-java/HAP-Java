package io.github.hapjava.accessories.optionalcharacteristic;

import io.github.hapjava.characteristics.HomekitCharacteristicChangeCallback;
import io.github.hapjava.characteristics.impl.inputsource.TargetVisibilityStateEnum;
import java.util.concurrent.CompletableFuture;

/** accessory with target visibility state characteristics. */
public interface AccessoryWithTargetVisibilityState {

  /**
   * Retrieves the target visibility state.
   *
   * @return a future that will contain the target visibility state
   */
  CompletableFuture<TargetVisibilityStateEnum> getTargetVisibilityState();

  /**
   * Sets the target visibility state
   *
   * @param state the target visibility state to set
   * @return a future that completes when the change is made
   * @throws Exception when the change cannot be made
   */
  CompletableFuture<Void> setTargetVisibilityState(TargetVisibilityStateEnum state)
      throws Exception;

  /**
   * Subscribes to changes in t target visibility state.
   *
   * @param callback the function to call when the target visibility state changes.
   */
  void subscribeTargetVisibilityState(HomekitCharacteristicChangeCallback callback);

  /** Unsubscribes from changes in the target visibility state. */
  void unsubscribeTargetVisibilityState();
}
