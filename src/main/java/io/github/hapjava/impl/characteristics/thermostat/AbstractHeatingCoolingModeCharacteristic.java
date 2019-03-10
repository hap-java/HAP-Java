package io.github.hapjava.impl.characteristics.thermostat;

import io.github.hapjava.accessories.properties.ThermostatMode;
import io.github.hapjava.characteristics.EnumCharacteristic;
import io.github.hapjava.characteristics.EventableCharacteristic;
import java.util.concurrent.CompletableFuture;

abstract class AbstractHeatingCoolingModeCharacteristic extends EnumCharacteristic
    implements EventableCharacteristic {

  public AbstractHeatingCoolingModeCharacteristic(
      String type, boolean isWritable, String description) {
    super(type, isWritable, true, description, 3);
  }

  @Override
  protected final void setValue(Integer value) throws Exception {
    setModeValue(ThermostatMode.fromCode(value));
  }

  @Override
  protected final CompletableFuture<Integer> getValue() {
    return getModeValue().thenApply(t -> t.getCode());
  }

  protected abstract void setModeValue(ThermostatMode mode) throws Exception;

  protected abstract CompletableFuture<ThermostatMode> getModeValue();
}
