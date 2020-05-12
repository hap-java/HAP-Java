package io.github.hapjava.accessories.optionalcharacteristic;

import io.github.hapjava.characteristics.HomekitCharacteristicChangeCallback;
import io.github.hapjava.characteristics.impl.thermostat.TemperatureDisplayUnitEnum;
import java.util.concurrent.CompletableFuture;

/**
 * Accessory with characteristic that describes units of temperature used for presentation purposes
 * (e.g. the units of temperature displayed on the screen).
 */
public interface AccessoryWithTemperatureDisplayUnits {

  /**
   * Retrieves temperature display units
   *
   * @return a future that will contain temperature display units
   */
  CompletableFuture<TemperatureDisplayUnitEnum> getTemperatureDisplayUnits();

  /**
   * Sets the temperature display units
   *
   * @param units the target temperature display units
   * @return a future that completes when the change is made
   * @throws Exception when the change cannot be made
   */
  CompletableFuture<Void> setTemperatureDisplayUnits(TemperatureDisplayUnitEnum units)
      throws Exception;

  /**
   * Subscribes to changes in the temperature display units
   *
   * @param callback the function to call when temperature display units changes.
   */
  void subscribeTemperatureDisplayUnits(HomekitCharacteristicChangeCallback callback);

  /** Unsubscribes from changes in the temperature display units */
  void unsubscribeTemperatureDisplayUnits();
}
