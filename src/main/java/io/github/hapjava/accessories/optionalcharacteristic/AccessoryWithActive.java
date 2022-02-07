package io.github.hapjava.accessories.optionalcharacteristic;

import io.github.hapjava.characteristics.HomekitCharacteristicChangeCallback;
import io.github.hapjava.characteristics.impl.common.ActiveEnum;
import java.util.concurrent.CompletableFuture;

/**
 * Accessory with active characteristic {@link
 * io.github.hapjava.characteristics.impl.common.ActiveCharacteristic}.
 */
public interface AccessoryWithActive {

  /**
   * Retrieves the active state (see {@link
   * io.github.hapjava.characteristics.impl.common.ActiveEnum} for supported values).
   *
   * @return a future that will contain the active state
   */
  CompletableFuture<ActiveEnum> getActive();

  /**
   * Set the active state (see {@link ActiveEnum} for supported values).
   *
   * @param active active state
   * @return a future that completes when the change is made
   */
  CompletableFuture<Void> setActive(ActiveEnum active);

  /**
   * Subscribes to changes in the active state.
   *
   * @param callback the function to call when the active state changes.
   */
  void subscribeActive(HomekitCharacteristicChangeCallback callback);

  /** Unsubscribes from changes in the active state. */
  void unsubscribeActive();
}
