package io.github.hapjava.accessories;

import io.github.hapjava.characteristics.HomekitCharacteristicChangeCallback;
import io.github.hapjava.characteristics.impl.common.ProgrammableSwitchEnum;
import io.github.hapjava.services.Service;
import io.github.hapjava.services.impl.DoorbellService;
import java.util.Collection;
import java.util.Collections;
import java.util.concurrent.CompletableFuture;

/** doorbell accessory with a switch (button push) event. */
public interface DoorbellAccessory extends HomekitAccessory {

  /**
   * Retrieves the last states of the doorbell. Bluetooth device should return the last event, the
   * IP device should always return null
   *
   * @return state of the door bell event
   */
  CompletableFuture<ProgrammableSwitchEnum> getSwitchEvent();

  /**
   * Subscribes to changes in doorbell switch event, i.e. pressing on the door bell button.
   *
   * @param callback the function to call when the state changes.
   */
  void subscribeSwitchEvent(HomekitCharacteristicChangeCallback callback);

  /** Unsubscribes from changes in doorbell switch event. */
  void unsubscribeSwitchEvent();

  @Override
  default Collection<Service> getServices() {
    return Collections.singleton(new DoorbellService(this));
  }
}
