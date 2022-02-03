package io.github.hapjava.accessories.optionalcharacteristic;

import io.github.hapjava.characteristics.HomekitCharacteristicChangeCallback;
import io.github.hapjava.characteristics.impl.television.ClosedCaptionsEnum;
import java.util.concurrent.CompletableFuture;

/**
 * Accessory with closed captions characteristic {@link
 * io.github.hapjava.characteristics.impl.television.ClosedCaptionsCharacteristic}.
 */
public interface AccessoryWithClosedCaptions {

  /**
   * Retrieves the closed captions state (see {@link ClosedCaptionsEnum} for supported values).
   *
   * @return a future that will contain the closed captions
   */
  CompletableFuture<ClosedCaptionsEnum> getClosedCaptions();

  /**
   * Set the closed captions state (see {@link ClosedCaptionsEnum} for supported values).
   *
   * @param closedCaptions closed captions
   * @return a future that completes when the change is made
   */
  CompletableFuture<Void> setClosedCaptions(ClosedCaptionsEnum closedCaptions);

  /**
   * Subscribes to changes in the closed captions.
   *
   * @param callback the function to call when the closed captions changes.
   */
  void subscribeClosedCaptions(HomekitCharacteristicChangeCallback callback);

  /** Unsubscribes from changes in the closed captions. */
  void unsubscribeClosedCaptions();
}
