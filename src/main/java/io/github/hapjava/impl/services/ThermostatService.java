package io.github.hapjava.impl.services;

import io.github.hapjava.accessories.thermostat.BasicThermostat;
import io.github.hapjava.accessories.thermostat.CoolingThermostat;
import io.github.hapjava.accessories.thermostat.HeatingThermostat;
import io.github.hapjava.impl.characteristics.thermostat.*;

public class ThermostatService extends AbstractServiceImpl {

  public ThermostatService(BasicThermostat thermostat) {
    this(thermostat, thermostat.getLabel());
  }

  public ThermostatService(BasicThermostat thermostat, String serviceName) {
    super("0000004A-0000-1000-8000-0026BB765291", thermostat, serviceName);
    addCharacteristic(new CurrentHeatingCoolingModeCharacteristic(thermostat));
    addCharacteristic(new CurrentTemperatureCharacteristic(thermostat));
    addCharacteristic(new TargetHeatingCoolingModeCharacteristic(thermostat));
    addCharacteristic(new TargetTemperatureCharacteristic(thermostat));
    addCharacteristic(new TemperatureUnitsCharacteristic(thermostat));
    if (thermostat instanceof HeatingThermostat) {
      addCharacteristic(
          new HeatingThresholdTemperatureCharacteristic((HeatingThermostat) thermostat));
    }
    if (thermostat instanceof CoolingThermostat) {
      addCharacteristic(
          new CoolingThresholdTemperatureCharacteristic((CoolingThermostat) thermostat));
    }
  }
}
