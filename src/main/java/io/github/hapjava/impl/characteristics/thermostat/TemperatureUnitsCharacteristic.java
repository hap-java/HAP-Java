package io.github.hapjava.impl.characteristics.thermostat;

import io.github.hapjava.accessories.thermostat.BasicThermostat;
import io.github.hapjava.characteristics.EnumCharacteristic;
import java.util.concurrent.CompletableFuture;

public class TemperatureUnitsCharacteristic extends EnumCharacteristic {

  private final BasicThermostat thermostat;

  public TemperatureUnitsCharacteristic(BasicThermostat thermostat) {
    super("00000036-0000-1000-8000-0026BB765291", false, true, "The temperature unit", 1);
    this.thermostat = thermostat;
  }

  @Override
  protected void setValue(Integer value) throws Exception {
    // Not writable
  }

  @Override
  protected CompletableFuture<Integer> getValue() {
    return CompletableFuture.completedFuture(thermostat.getTemperatureUnit().getCode());
  }
}
