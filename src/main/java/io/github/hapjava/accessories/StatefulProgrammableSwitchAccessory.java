package io.github.hapjava.accessories;

import io.github.hapjava.characteristics.HomekitCharacteristicChangeCallback;
import io.github.hapjava.characteristics.impl.common.ProgrammableSwitchEnum;
import io.github.hapjava.services.Service;
import io.github.hapjava.services.impl.StatefulProgrammableSwitchService;
import java.util.Collection;
import java.util.Collections;
import java.util.concurrent.CompletableFuture;

/**
 * The Stateful Programmable Switch accessory: a programmable switch that also keeps an output state
 * (0 or 1).
 *
 * <p>This service is not part of the public HAP specification and the Apple Home app may not
 * display it; third-party HomeKit apps can use it.
 */
public interface StatefulProgrammableSwitchAccessory extends HomekitAccessory {

  /**
   * Retrieves the last states of the switch. Bluetooth device should return the last event, the IP
   * device should always return null
   *
   * @return state of the switch event
   */
  CompletableFuture<ProgrammableSwitchEnum> getSwitchEvent();

  /**
   * Subscribes to changes in switch event, i.e. pressing on the button.
   *
   * @param callback the function to call when the state changes.
   */
  void subscribeSwitchEvent(HomekitCharacteristicChangeCallback callback);

  /** Unsubscribes from changes in switch event. */
  void unsubscribeSwitchEvent();

  /**
   * Retrieves the output state of the switch.
   *
   * @return a future with the output state, 0 or 1
   */
  CompletableFuture<Integer> getSwitchOutputState();

  /**
   * Sets the output state of the switch.
   *
   * @param state the output state to set, 0 or 1
   * @return a future that completes when the change is made
   * @throws Exception when the output state cannot be set
   */
  CompletableFuture<Void> setSwitchOutputState(int state) throws Exception;

  /**
   * Subscribes to changes in the output state of the switch.
   *
   * @param callback the function to call when the state changes.
   */
  void subscribeSwitchOutputState(HomekitCharacteristicChangeCallback callback);

  /** Unsubscribes from changes in the output state of the switch. */
  void unsubscribeSwitchOutputState();

  @Override
  default Collection<Service> getServices() {
    return Collections.singleton(new StatefulProgrammableSwitchService(this));
  }
}
