package com.beowulfe.hap.impl.characteristics.thermostat;

import com.beowulfe.hap.HomekitCharacteristicChangeCallback;
import com.beowulfe.hap.accessories.thermostat.HeatingThermostat;
import java.util.concurrent.CompletableFuture;

public class HeatingThresholdTemperatureCharacteristic extends AbstractTemperatureCharacteristic {

  private final HeatingThermostat thermostat;

  public HeatingThresholdTemperatureCharacteristic(HeatingThermostat thermostat) {
    super(
        "00000012-0000-1000-8000-0026BB765291",
        true,
        "Temperature below which heating will be active",
        thermostat);
    this.thermostat = thermostat;
  }

  @Override
  public void subscribe(HomekitCharacteristicChangeCallback callback) {
    thermostat.subscribeHeatingThresholdTemperature(callback);
  }

  @Override
  public void unsubscribe() {
    thermostat.unsubscribeHeatingThresholdTemperature();
  }

  @Override
  protected CompletableFuture<Double> getDoubleValue() {
    return thermostat.getHeatingThresholdTemperature();
  }

  @Override
  protected void setValue(Double value) throws Exception {
    thermostat.setHeatingThresholdTemperature(value);
  }
}
