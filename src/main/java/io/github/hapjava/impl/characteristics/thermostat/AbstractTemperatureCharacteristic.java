package io.github.hapjava.impl.characteristics.thermostat;

import io.github.hapjava.accessories.TemperatureSensor;
import io.github.hapjava.characteristics.EventableCharacteristic;
import io.github.hapjava.characteristics.FloatCharacteristic;

public abstract class AbstractTemperatureCharacteristic extends FloatCharacteristic
    implements EventableCharacteristic {

  public AbstractTemperatureCharacteristic(
      String type, boolean isWritable, String description, TemperatureSensor sensor) {
    super(
        type,
        isWritable,
        true,
        description,
        sensor.getMinimumTemperature(),
        sensor.getMaximumTemperature(),
        0.1,
        "celsius");
  }
}
