package io.github.hapjava.accessories;

import io.github.hapjava.accessories.thermostat.*;

/**
 * A thermostat with heating and cooling controls.
 *
 * @author Andy Lintner
 * @deprecated Use {@link BasicThermostat}, {@link HeatingThermostat}, and {@link CoolingThermostat}
 *     instead
 */
@Deprecated
public interface Thermostat extends BasicThermostat, HeatingThermostat, CoolingThermostat {}
