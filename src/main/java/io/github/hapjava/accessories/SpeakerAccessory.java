package io.github.hapjava.accessories;

import io.github.hapjava.characteristics.HomekitCharacteristicChangeCallback;
import io.github.hapjava.services.Service;
import io.github.hapjava.services.impl.SpeakerService;
import java.util.Collection;
import java.util.Collections;
import java.util.concurrent.CompletableFuture;

/** Speaker accessory. */
public interface SpeakerAccessory extends HomekitAccessory {

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

  @Override
  default Collection<Service> getServices() {
    return Collections.singleton(new SpeakerService(this));
  }
}
