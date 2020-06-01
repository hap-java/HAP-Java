package io.github.hapjava.characteristics.impl.thermostat;

import io.github.hapjava.characteristics.CharacteristicEnum;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

/** 0 ”Off.” 1 ”Heat. The Heater is currently on.” 2 ”Cool. Cooler is currently on.” */
public enum CurrentHeatingCoolingStateEnum implements CharacteristicEnum {
  OFF(0),
  HEAT(1),
  COOL(2);

  private static final Map<Integer, CurrentHeatingCoolingStateEnum> reverse;

  static {
    reverse =
        Arrays.stream(CurrentHeatingCoolingStateEnum.values())
            .collect(Collectors.toMap(CurrentHeatingCoolingStateEnum::getCode, t -> t));
  }

  public static CurrentHeatingCoolingStateEnum fromCode(Integer code) {
    return reverse.get(code);
  }

  private final int code;

  CurrentHeatingCoolingStateEnum(int code) {
    this.code = code;
  }

  public int getCode() {
    return code;
  }
}
