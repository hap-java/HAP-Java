package io.github.hapjava.accessories;

import io.github.hapjava.characteristics.HomekitCharacteristicChangeCallback;
import io.github.hapjava.characteristics.impl.thermostat.CurrentHeatingCoolingStateEnum;
import io.github.hapjava.characteristics.impl.thermostat.TargetHeatingCoolingStateEnum;
import io.github.hapjava.characteristics.impl.thermostat.TemperatureDisplayUnitEnum;
import io.github.hapjava.services.Service;
import io.github.hapjava.services.impl.ThermostatService;
import java.util.Collection;
import java.util.Collections;
import java.util.concurrent.CompletableFuture;

/**
 * A thermostat with heating and cooling controls.
 *
 * @author Andy Lintner
 */
public interface ThermostatAccessory extends HomekitAccessory {

  /**
   * Retrieves the current temperature, in celsius degrees.
   *
   * @return a future that will contain the temperature.
   */
  CompletableFuture<Double> getCurrentTemperature();

  /**
   * return the min value for current temperature. overwrite if you want to change the default
   * value.
   *
   * @return min current temperature
   */
  default double getMinCurrentTemperature() {
    return 0;
  }

  /**
   * return the max value for current temperature. overwrite if you want to change the default
   * value.
   *
   * @return max current temperature
   */
  default double getMaxCurrentTemperature() {
    return 100;
  }

  /**
   * return the min step value for current temperature. overwrite if you want to change the default
   * value.
   *
   * @return min step current temperature
   */
  default double getMinStepCurrentTemperature() {
    return 0.1;
  }

  /**
   * Retrieves the target temperature, in celsius degrees.
   *
   * @return a future that will contain the target temperature.
   */
  CompletableFuture<Double> getTargetTemperature();

  /**
   * Sets the target temperature.
   *
   * @param value the target temperature, in celsius degrees.
   * @throws Exception when the temperature cannot be changed.
   */
  void setTargetTemperature(Double value) throws Exception;

  /**
   * return the min value for target temperature. overwrite if you want to change the default value.
   *
   * @return min target temperature
   */
  default double getMinTargetTemperature() {
    return 10;
  }

  /**
   * return the max value for target temperature. overwrite if you want to change the default value.
   *
   * @return max target temperature
   */
  default double getMaxTargetTemperature() {
    return 38;
  }

  /**
   * return the min step value for target temperature. overwrite if you want to change the default
   * value.
   *
   * @return min step target temperature
   */
  default double getMinStepTargetTemperature() {
    return 0.1;
  }

  /**
   * Retrieves the current {@link CurrentHeatingCoolingStateEnum} of the thermostat.
   *
   * @return a future that will contain the mode.
   */
  CompletableFuture<CurrentHeatingCoolingStateEnum> getCurrentState();

  /**
   * Sets the {@link TargetHeatingCoolingStateEnum} of the thermostat.
   *
   * @param mode The {@link TargetHeatingCoolingStateEnum} to set.
   * @throws Exception when the change cannot be made.
   */
  void setTargetState(TargetHeatingCoolingStateEnum mode) throws Exception;

  /**
   * Retrieves the pending, but not yet complete, {@link TargetHeatingCoolingStateEnum} of the
   * thermostat.
   *
   * @return a future that will contain the target mode.
   */
  CompletableFuture<TargetHeatingCoolingStateEnum> getTargetState();

  /**
   * Retrieves the temperture display units, e.g. C or F.
   *
   * @return a future that will contain the temperature unit.
   */
  CompletableFuture<TemperatureDisplayUnitEnum> getTemperatureDisplayUnit();

  /**
   * Sets the temperature unit.
   *
   * @param value the temperature display unit.
   * @throws Exception when the temperature cannot be changed.
   */
  void setTemperatureDisplayUnit(TemperatureDisplayUnitEnum value) throws Exception;

  /**
   * Subscribes to changes in the pending, but not yet complete, {@link
   * TargetHeatingCoolingStateEnum} of the thermostat.
   *
   * @param callback the function to call when the state changes.
   */
  void subscribeTargetState(HomekitCharacteristicChangeCallback callback);

  /**
   * Subscribes to changes in the target temperature.
   *
   * @param callback the function to call when the state changes.
   */
  void subscribeTargetTemperature(HomekitCharacteristicChangeCallback callback);

  /**
   * Subscribes to changes in the temperature display unit.
   *
   * @param callback the function to call when the uni changes.
   */
  void subscribeTemperatureDisplayUnit(HomekitCharacteristicChangeCallback callback);

  /**
   * Subscribes to changes in the current temperature.
   *
   * @param callback the function to call when the state changes.
   */
  void subscribeCurrentTemperature(HomekitCharacteristicChangeCallback callback);

  /**
   * Subscribes to changes in the {@link CurrentHeatingCoolingStateEnum} of the thermostat.
   *
   * @param callback the function to call when the state changes.
   */
  void subscribeCurrentState(HomekitCharacteristicChangeCallback callback);

  /** Unsubscribes from changes in the mode of the thermostat. */
  void unsubscribeCurrentState();

  /**
   * Unsubscribes from changes in the pending, but not yet complete, {@link
   * TargetHeatingCoolingStateEnum} of the thermostat.
   */
  void unsubscribeTargetState();

  /** Unsubscribes from changes in the current temperature. */
  void unsubscribeCurrentTemperature();

  /** Unsubscribes from changes in temperature display unit */
  void unsubscribeTemperatureDisplayUnit();

  /** Unsubscribes from changes in the target temperature. */
  void unsubscribeTargetTemperature();

  /**
   * Valid values for current state.
   *
   * @return array of valid current states.
   */
  default CurrentHeatingCoolingStateEnum[] getCurrentHeatingCoolingStateValidValues() {
    return CurrentHeatingCoolingStateEnum.values();
  }

  /**
   * Valid values for target state.
   *
   * @return array of valid targe states.
   */
  default TargetHeatingCoolingStateEnum[] getTargetHeatingCoolingStateValidValues() {
    return TargetHeatingCoolingStateEnum.values();
  }

  @Override
  default Collection<Service> getServices() {
    return Collections.singleton(new ThermostatService(this));
  }
}
