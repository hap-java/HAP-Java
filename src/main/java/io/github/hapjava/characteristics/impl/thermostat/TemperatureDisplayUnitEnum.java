package io.github.hapjava.characteristics.impl.thermostat;

import io.github.hapjava.accessories.TemperatureSensorAccessory;
import io.github.hapjava.characteristics.CharacteristicEnum;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * The temperature unit used by a {@link TemperatureSensorAccessory}.
 *
 * <p>0 ”Celsius” 1 ”Fahrenheit”
 *
 * @author Andy Lintner
 */
public enum TemperatureDisplayUnitEnum implements CharacteristicEnum {
  CELSIUS(0),
  FAHRENHEIT(1);

  private static final Map<Integer, TemperatureDisplayUnitEnum> reverse;

  static {
    reverse =
        Arrays.stream(TemperatureDisplayUnitEnum.values())
            .collect(Collectors.toMap(t -> t.getCode(), t -> t));
  }

  static TemperatureDisplayUnitEnum fromCode(Integer code) {
    return reverse.get(code);
  }

  private final int code;

  private TemperatureDisplayUnitEnum(int code) {
    this.code = code;
  }

  public int getCode() {
    return code;
  }
}
