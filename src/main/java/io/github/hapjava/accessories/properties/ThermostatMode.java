package io.github.hapjava.accessories.properties;

import io.github.hapjava.accessories.thermostat.BasicThermostat;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * The mode used by a {@link BasicThermostat}
 *
 * @author Andy Lintner
 */
public enum ThermostatMode {
  OFF(0),
  HEAT(1),
  COOL(2),
  AUTO(3);

  private static final Map<Integer, ThermostatMode> reverse;

  static {
    reverse =
        Arrays.stream(ThermostatMode.values()).collect(Collectors.toMap(t -> t.getCode(), t -> t));
  }

  public static ThermostatMode fromCode(Integer code) {
    return reverse.get(code);
  }

  private final int code;

  private ThermostatMode(int code) {
    this.code = code;
  }

  public int getCode() {
    return code;
  }
}
