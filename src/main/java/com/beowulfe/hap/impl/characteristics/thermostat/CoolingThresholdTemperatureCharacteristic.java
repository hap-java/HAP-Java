package com.beowulfe.hap.impl.characteristics.thermostat;

import com.beowulfe.hap.HomekitCharacteristicChangeCallback;
import com.beowulfe.hap.accessories.thermostat.CoolingThermostat;
import java.util.concurrent.CompletableFuture;

public class CoolingThresholdTemperatureCharacteristic extends AbstractTemperatureCharacteristic {

  private final CoolingThermostat thermostat;

  public CoolingThresholdTemperatureCharacteristic(CoolingThermostat thermostat) {
    super(
        "0000000D-0000-1000-8000-0026BB765291",
        true,
        "Temperature above which cooling will be active",
        thermostat);
    this.thermostat = thermostat;
  }

  @Override
  public void subscribe(HomekitCharacteristicChangeCallback callback) {
    thermostat.subscribeCoolingThresholdTemperature(callback);
  }

  @Override
  public void unsubscribe() {
    thermostat.unsubscribeCoolingThresholdTemperature();
  }

  @Override
  protected CompletableFuture<Double> getDoubleValue() {
    return thermostat.getCoolingThresholdTemperature();
  }

  @Override
  protected void setValue(Double value) throws Exception {
    thermostat.setCoolingThresholdTemperature(value);
  }
}
