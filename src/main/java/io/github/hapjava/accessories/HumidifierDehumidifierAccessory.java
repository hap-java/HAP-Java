package io.github.hapjava.accessories;

import io.github.hapjava.characteristics.HomekitCharacteristicChangeCallback;
import io.github.hapjava.characteristics.impl.humidifier.CurrentHumidifierDehumidifierStateEnum;
import io.github.hapjava.characteristics.impl.humidifier.TargetHumidifierDehumidifierStateEnum;
import io.github.hapjava.services.Service;
import io.github.hapjava.services.impl.HumidifierDehumidifierService;
import java.util.Collection;
import java.util.Collections;
import java.util.concurrent.CompletableFuture;

/**
 * Humidifier/Dehumidifier accessory
 *
 * @author Eugen Freiter
 */
public interface HumidifierDehumidifierAccessory extends HomekitAccessory {
  /**
   * Retrieves the current relative humidity.
   *
   * @return a future that will contain the humidity as a value between 0 and 100.
   */
  CompletableFuture<Double> getCurrentHumidity();

  /**
   * Mandatory: Retrieves the current active state of the humidifier/dehumidifier.
   *
   * @return a future that will contain the binary state
   */
  CompletableFuture<Boolean> isActive();

  /**
   * Sets the active state of the humidifier/dehumidifier
   *
   * @param state the binary state to set
   * @return a future that completes when the change is made
   * @throws Exception when the change cannot be made
   */
  CompletableFuture<Void> setActive(boolean state) throws Exception;

  /**
   * Retrieves the humidifier/dehumidifier current state.
   *
   * @return a future that will contain the humidifier/dehumidifier current state .
   */
  CompletableFuture<CurrentHumidifierDehumidifierStateEnum> getCurrentHumidifierDehumidifierState();

  /**
   * Retrieves the humidifier/dehumidifier target state.
   *
   * @return a future that will contain the humidifier/dehumidifier target state .
   */
  CompletableFuture<TargetHumidifierDehumidifierStateEnum> getTargetHumidifierDehumidifierState();

  /**
   * set humidifier/dehumidifier target state the lock target state.
   *
   * @param state humidifier/dehumidifier target state
   * @return a future that completes when the change is made
   */
  CompletableFuture<Void> setTargetHumidifierDehumidifierState(
      TargetHumidifierDehumidifierStateEnum state);

  /**
   * Subscribes to changes in the humidifier/dehumidifier current state.
   *
   * @param callback the function to call when the state changes.
   */
  void subscribeCurrentHumidifierDehumidifierState(HomekitCharacteristicChangeCallback callback);

  /** Unsubscribes from changes in humidifier/dehumidifier current state. */
  void unsubscribeCurrentHumidifierDehumidifierState();

  /**
   * Subscribes to changes in the humidifier/dehumidifier target state.
   *
   * @param callback the function to call when the state changes.
   */
  void subscribeTargetHumidifierDehumidifierState(HomekitCharacteristicChangeCallback callback);

  /** Unsubscribes from changes in humidifier/dehumidifier target state. */
  void unsubscribeTargetHumidifierDehumidifierState();

  /**
   * Subscribes to changes in the active state of the humidifier/dehumidifier .
   *
   * @param callback the function to call when the active state changes.
   */
  void subscribeActive(HomekitCharacteristicChangeCallback callback);

  /** Unsubscribes from changes in the active state of the humidifier/dehumidifier . */
  void unsubscribeActive();

  /**
   * Subscribes to changes in the current humidity.
   *
   * @param callback the function to call when the state changes.
   */
  void subscribeCurrentHumidity(HomekitCharacteristicChangeCallback callback);

  /** Unsubscribes from changes in the current humidity. */
  void unsubscribeCurrentHumidity();

  @Override
  default Collection<Service> getServices() {
    return Collections.singleton(new HumidifierDehumidifierService(this));
  }
}
