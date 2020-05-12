package io.github.hapjava.accessories;

import io.github.hapjava.characteristics.HomekitCharacteristicChangeCallback;
import io.github.hapjava.characteristics.impl.heatercooler.CurrentHeaterCoolerStateEnum;
import io.github.hapjava.characteristics.impl.heatercooler.TargetHeaterCoolerStateEnum;
import io.github.hapjava.services.Service;
import io.github.hapjava.services.impl.HeaterCoolerService;
import java.util.Collection;
import java.util.Collections;
import java.util.concurrent.CompletableFuture;

/** Heater Cooler accessory */
public interface HeaterCoolerAccessory extends HomekitAccessory {
  /**
   * Retrieves the current temperature, in celsius degrees.
   *
   * @return a future that will contain the temperature.
   */
  CompletableFuture<Double> getCurrentTemperature();

  /**
   * Mandatory: Retrieves the current active state of the Heater Cooler.
   *
   * @return a future that will contain the binary state
   */
  CompletableFuture<Boolean> isActive();

  /**
   * Sets the active state of the Heater Cooler
   *
   * @param state the binary state to set
   * @return a future that completes when the change is made
   * @throws Exception when the change cannot be made
   */
  CompletableFuture<Void> setActive(boolean state) throws Exception;

  /**
   * Retrieves the heater /cooler current state.
   *
   * @return a future that will contain the heater cooler current state .
   */
  CompletableFuture<CurrentHeaterCoolerStateEnum> getCurrentHeaterCoolerState();

  /**
   * Retrieves the heater cooler target state.
   *
   * @return a future that will contain the heater cooler target state .
   */
  CompletableFuture<TargetHeaterCoolerStateEnum> getTargetHeaterCoolerState();

  /**
   * set heater cooler target state the lock target state.
   *
   * @param state heater cooler target state
   * @return a future that completes when the change is made
   */
  CompletableFuture<Void> setTargetHeaterCoolerState(TargetHeaterCoolerStateEnum state);

  /**
   * Subscribes to changes in the heater cooler current state.
   *
   * @param callback the function to call when the state changes.
   */
  void subscribeCurrentHeaterCoolerState(HomekitCharacteristicChangeCallback callback);

  /** Unsubscribes from changes in heater cooler current state. */
  void unsubscribeCurrentHeaterCoolerState();

  /**
   * Subscribes to changes in the heater cooler target state.
   *
   * @param callback the function to call when the state changes.
   */
  void subscribeTargetHeaterCoolerState(HomekitCharacteristicChangeCallback callback);

  /** Unsubscribes from changes in heater cooler target state. */
  void unsubscribeTargetHeaterCoolerState();

  /**
   * Subscribes to changes in the active state of the heater cooler .
   *
   * @param callback the function to call when the active state changes.
   */
  void subscribeActive(HomekitCharacteristicChangeCallback callback);

  /** Unsubscribes from changes in the active state of the heater cooler . */
  void unsubscribeActive();

  /**
   * Subscribes to changes in the current temperature.
   *
   * @param callback the function to call when the state changes.
   */
  void subscribeCurrentTemperature(HomekitCharacteristicChangeCallback callback);

  /** Unsubscribes from changes in the current temperature. */
  void unsubscribeCurrentTemperature();

  @Override
  default Collection<Service> getServices() {
    return Collections.singleton(new HeaterCoolerService(this));
  }
}
