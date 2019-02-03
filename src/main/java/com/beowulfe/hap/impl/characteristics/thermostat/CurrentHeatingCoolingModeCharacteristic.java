package com.beowulfe.hap.impl.characteristics.thermostat;

import com.beowulfe.hap.HomekitCharacteristicChangeCallback;
import com.beowulfe.hap.accessories.properties.ThermostatMode;
import com.beowulfe.hap.accessories.thermostat.BasicThermostat;
import com.beowulfe.hap.characteristics.EnumCharacteristic;
import com.beowulfe.hap.characteristics.EventableCharacteristic;
import java.util.concurrent.CompletableFuture;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CurrentHeatingCoolingModeCharacteristic extends EnumCharacteristic
    implements EventableCharacteristic {
  private static Logger logger =
      LoggerFactory.getLogger(CurrentHeatingCoolingModeCharacteristic.class);

  private final BasicThermostat thermostat;

  public CurrentHeatingCoolingModeCharacteristic(BasicThermostat thermostat) {
    super("0000000F-0000-1000-8000-0026BB765291", false, true, "Current Mode", 2);
    this.thermostat = thermostat;
  }

  @Override
  protected void setValue(Integer mode) throws Exception {
    // Not writable
  }

  @Override
  protected CompletableFuture<Integer> getValue() {
    return thermostat
        .getCurrentMode()
        .thenApply(
            t -> {
              if (t == ThermostatMode.AUTO) {
                logger.warn(
                    "CurrentHeatingCoolingMode characteristic cannot be ThermostatMode.AUTO; returning ThermostatMode.OFF");
                return ThermostatMode.OFF.getCode();
              } else {
                return t.getCode();
              }
            });
  }

  @Override
  public void subscribe(HomekitCharacteristicChangeCallback callback) {
    thermostat.subscribeCurrentMode(callback);
  }

  @Override
  public void unsubscribe() {
    thermostat.unsubscribeCurrentMode();
  }
}
