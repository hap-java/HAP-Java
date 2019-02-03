package com.beowulfe.hap.accessories.properties;

import com.beowulfe.hap.accessories.thermostat.BasicThermostat;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * The mode used by a {@link BasicThermostat}
 *
 * @author Andy Lintner
 */
public enum CurrentThermostatMode {
  OFF(0),
  HEAT(1),
  COOL(2);

  private static final Map<Integer, CurrentThermostatMode> reverse;

  static {
    reverse =
        Arrays.stream(CurrentThermostatMode.values()).collect(Collectors.toMap(t -> t.getCode(), t -> t));
  }

  public static CurrentThermostatMode fromCode(Integer code) {
    return reverse.get(code);
  }

  private final int code;

  private CurrentThermostatMode(int code) {
    this.code = code;
  }

  public int getCode() {
    return code;
  }
}
