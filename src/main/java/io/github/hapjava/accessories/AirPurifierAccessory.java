package io.github.hapjava.accessories;

import io.github.hapjava.characteristics.HomekitCharacteristicChangeCallback;
import io.github.hapjava.characteristics.impl.airpurifier.CurrentAirPurifierStateEnum;
import io.github.hapjava.characteristics.impl.airpurifier.TargetAirPurifierStateEnum;
import io.github.hapjava.services.Service;
import io.github.hapjava.services.impl.AirPurifierService;
import java.util.Collection;
import java.util.Collections;
import java.util.concurrent.CompletableFuture;

/** An air purifier. */
public interface AirPurifierAccessory extends HomekitAccessory {
  /**
   * Mandatory: Retrieves the current active state of the fan'.
   *
   * @return a future that will contain the binary state
   */
  CompletableFuture<Boolean> isActive();

  /**
   * Sets the active state of the fan
   *
   * @param state the binary state to set
   * @return a future that completes when the change is made
   * @throws Exception when the change cannot be made
   */
  CompletableFuture<Void> setActive(boolean state) throws Exception;

  /**
   * Subscribes to changes in the active state of the fan.
   *
   * @param callback the function to call when the direction changes.
   */
  void subscribeActive(HomekitCharacteristicChangeCallback callback);

  /** Unsubscribes from changes in the active state of the fan. */
  void unsubscribeActive();

  /**
   * Retrieves the current state of the air purifier
   *
   * @return a future that will contain the state
   */
  CompletableFuture<CurrentAirPurifierStateEnum> getCurrentState();

  /**
   * Subscribes to changes in the state of the air purifier.
   *
   * @param callback the function to call when the state changes.
   */
  void subscribeCurrentState(HomekitCharacteristicChangeCallback callback);

  /** Unsubscribes from changes in the state of the air purifier. */
  void unsubscribeCurrentState();

  /**
   * Retrieves the air purifier target state.
   *
   * @return a future that will contain the air purifier target state .
   */
  CompletableFuture<TargetAirPurifierStateEnum> getTargetState();

  /**
   * set target state the air purifier target state.
   *
   * @param state air purifier target state
   * @return a future that completes when the change is made
   */
  CompletableFuture<Void> setTargetState(TargetAirPurifierStateEnum state);

  /**
   * Subscribes to changes in the target state of the air purifier.
   *
   * @param callback the function to call when the target state changes.
   */
  void subscribeTargetState(HomekitCharacteristicChangeCallback callback);

  /** Unsubscribes from changes in the target state of the air purifier. */
  void unsubscribeTargetState();

  /**
   * If a filter maintenance service is needed as a linked service to this AirPurifier, this is the
   * place.
   *
   * @return an instance of FilterMaintenanceAccessory, null if not needed.
   */
  default FilterMaintenanceAccessory getFilterMaintenanceAccessory() {
    return null;
  };

  @Override
  default Collection<Service> getServices() {
    AirPurifierService service = new AirPurifierService(this);
    FilterMaintenanceAccessory fmAccessory = this.getFilterMaintenanceAccessory();
    if (fmAccessory != null) {
      service.addLinkedService(fmAccessory.getPrimaryService());
    }
    return Collections.singleton(service);
  }
}
