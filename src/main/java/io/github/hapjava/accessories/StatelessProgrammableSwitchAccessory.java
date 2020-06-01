package io.github.hapjava.accessories;

import io.github.hapjava.characteristics.HomekitCharacteristicChangeCallback;
import io.github.hapjava.characteristics.impl.common.ProgrammableSwitchEnum;
import io.github.hapjava.services.Service;
import io.github.hapjava.services.impl.StatelessProgrammableSwitchService;
import java.util.Collection;
import java.util.Collections;
import java.util.concurrent.CompletableFuture;

/** The Stateless Programmable Switch accessory */
public interface StatelessProgrammableSwitchAccessory extends HomekitAccessory {

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

  @Override
  default Collection<Service> getServices() {
    return Collections.singleton(new StatelessProgrammableSwitchService(this));
  }
}
