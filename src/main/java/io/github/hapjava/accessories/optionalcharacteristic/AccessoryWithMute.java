package io.github.hapjava.accessories.optionalcharacteristic;

import io.github.hapjava.characteristics.HomekitCharacteristicChangeCallback;
import java.util.concurrent.CompletableFuture;

/**
 * Accessory with mute characteristic {@link
 * io.github.hapjava.characteristics.impl.audio.MuteCharacteristic}.
 */
public interface AccessoryWithMute {

  /**
   * Retrieves mute status.
   *
   * @return true if accessory is muted
   */
  CompletableFuture<Boolean> isMuted();

  /**
   * Sets the mute status
   *
   * @param mute true if accessory should be muted
   * @return a future that completes when the change is made
   * @throws Exception when the change cannot be made
   */
  CompletableFuture<Void> setMute(boolean mute) throws Exception;

  /**
   * Subscribes to changes in mute state.
   *
   * @param callback the function to call when the state changes.
   */
  void subscribeMuteState(HomekitCharacteristicChangeCallback callback);

  /** Unsubscribes from changes in the mute state. */
  void unsubscribeMuteState();
}
