package io.github.hapjava.accessories;

import io.github.hapjava.characteristics.HomekitCharacteristicChangeCallback;
import io.github.hapjava.services.Service;
import io.github.hapjava.services.impl.LightbulbService;
import java.util.Collection;
import java.util.Collections;
import java.util.concurrent.CompletableFuture;

/**
 * A simple light with a binary state.
 *
 * @author Andy Lintner
 */
public interface LightbulbAccessory extends HomekitAccessory {

  /**
   * Retrieves the current binary state of the light.
   *
   * @return a future that will contain the binary state
   */
  CompletableFuture<Boolean> getLightbulbPowerState();

  /**
   * Sets the binary state of the light
   *
   * @param powerState the binary state to set
   * @return a future that completes when the change is made
   * @throws Exception when the change cannot be made
   */
  CompletableFuture<Void> setLightbulbPowerState(boolean powerState) throws Exception;

  /**
   * Subscribes to changes in the binary state of the light.
   *
   * @param callback the function to call when the state changes.
   */
  void subscribeLightbulbPowerState(HomekitCharacteristicChangeCallback callback);

  /** Unsubscribes from changes in the binary state of the light. */
  void unsubscribeLightbulbPowerState();

  @Override
  default Collection<Service> getServices() {
    return Collections.singleton(new LightbulbService(this));
  }
}
