package io.github.hapjava.characteristics.impl.thermostat;

import io.github.hapjava.characteristics.CharacteristicEnum;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 0 ”Off” 1 ”Heat. If the current temperature is below the target temperature then turn on
 * heating.” 2 ”Cool. If the current temperature is above the target temperature then turn on
 * cooling.” 3 ”Auto. Turn on heating or cooling to maintain temperature within the heating and
 * cooling threshold of the target temperature.” 4-255 ”Reserved”
 *
 * @author Andy Lintner
 */
public enum TargetHeatingCoolingStateEnum implements CharacteristicEnum {
  OFF(0),
  HEAT(1),
  COOL(2),
  AUTO(3);

  private static final Map<Integer, TargetHeatingCoolingStateEnum> reverse;

  static {
    reverse =
        Arrays.stream(TargetHeatingCoolingStateEnum.values())
            .collect(Collectors.toMap(t -> t.getCode(), t -> t));
  }

  public static TargetHeatingCoolingStateEnum fromCode(Integer code) {
    return reverse.get(code);
  }

  private final int code;

  private TargetHeatingCoolingStateEnum(int code) {
    this.code = code;
  }

  public int getCode() {
    return code;
  }
}
