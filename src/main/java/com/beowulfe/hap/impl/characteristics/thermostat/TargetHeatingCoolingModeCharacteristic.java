package com.beowulfe.hap.impl.characteristics.thermostat;

import com.beowulfe.hap.HomekitCharacteristicChangeCallback;
import com.beowulfe.hap.accessories.properties.ThermostatMode;
import com.beowulfe.hap.accessories.thermostat.BasicThermostat;
import com.beowulfe.hap.characteristics.EnumCharacteristic;
import com.beowulfe.hap.characteristics.EventableCharacteristic;
import java.util.concurrent.CompletableFuture;

public class TargetHeatingCoolingModeCharacteristic extends EnumCharacteristic
    implements EventableCharacteristic {

  private final BasicThermostat thermostat;

  public TargetHeatingCoolingModeCharacteristic(BasicThermostat thermostat) {
    super("00000033-0000-1000-8000-0026BB765291", true, true, "Target Mode", 3);
    this.thermostat = thermostat;
  }

  @Override
  protected void setValue(Integer code) throws Exception {
    thermostat.setTargetMode(ThermostatMode.fromCode(code));
  }

  @Override
  protected CompletableFuture<Integer> getValue() {
    return thermostat.getTargetMode().thenApply(targetMode -> targetMode.getCode());
  }

  @Override
  public void subscribe(HomekitCharacteristicChangeCallback callback) {
    thermostat.subscribeTargetMode(callback);
  }

  @Override
  public void unsubscribe() {
    thermostat.unsubscribeTargetMode();
  }
}
