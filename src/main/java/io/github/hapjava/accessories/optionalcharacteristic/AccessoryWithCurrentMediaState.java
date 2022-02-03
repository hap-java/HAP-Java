package io.github.hapjava.accessories.optionalcharacteristic;

import io.github.hapjava.characteristics.HomekitCharacteristicChangeCallback;
import io.github.hapjava.characteristics.impl.television.CurrentMediaStateEnum;
import java.util.concurrent.CompletableFuture;

/**
 * Accessory with current media state characteristic {@link
 * io.github.hapjava.characteristics.impl.television.CurrentMediaStateCharacteristic}.
 */
public interface AccessoryWithCurrentMediaState {

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
}
