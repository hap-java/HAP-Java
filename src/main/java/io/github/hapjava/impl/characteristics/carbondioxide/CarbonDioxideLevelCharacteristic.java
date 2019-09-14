package com.beowulfe.hap.impl.characteristics.carbondioxide;

import com.beowulfe.hap.HomekitCharacteristicChangeCallback;
import com.beowulfe.hap.accessories.CarbonDioxideSensor;
import com.beowulfe.hap.characteristics.EventableCharacteristic;
import com.beowulfe.hap.characteristics.FloatCharacteristic;
import java.util.concurrent.CompletableFuture;

public class CarbonDioxideLevelCharacteristic extends FloatCharacteristic
    implements EventableCharacteristic {

  private final CarbonDioxideSensor sensor;

  public CarbonDioxideLevelCharacteristic(CarbonDioxideSensor sensor) {
    super(
        "00000093-0000-1000-8000-0026BB765291",
        false,
        true,
        "Carbon Dioxide level",
        0,
        100000,
        0.1,
        "%");
    this.sensor = sensor;
  }

  @Override
  public void subscribe(HomekitCharacteristicChangeCallback callback) {
    sensor.subscribeCarbonDioxideLevel(callback);
  }

  @Override
  public void unsubscribe() {
    sensor.unsubscribeCarbonDioxideLevel();
  }

  @Override
  protected void setValue(Double value) throws Exception {
    // Read Only
  }

  @Override
  protected CompletableFuture<Double> getDoubleValue() {
    return sensor.getCarbonDioxideLevel();
  }

  @Override
  public String toString() {
    return "CarbonDioxideLevelCharacteristic{"
        + "sensor level ="
        + sensor.getCarbonDioxideLevel()
        + '}';
  }
}
