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
public enum TargetThermostatMode {
  OFF(0),
  HEAT(1),
  COOL(2),
  AUTO(3);

  private static final Map<Integer, TargetThermostatMode> reverse;

  static {
    reverse =
        Arrays.stream(TargetThermostatMode.values())
            .collect(Collectors.toMap(t -> t.getCode(), t -> t));
  }

  public static TargetThermostatMode fromCode(Integer code) {
    return reverse.get(code);
  }

  private final int code;

  private TargetThermostatMode(int code) {
    this.code = code;
  }

  public int getCode() {
    return code;
  }
}
