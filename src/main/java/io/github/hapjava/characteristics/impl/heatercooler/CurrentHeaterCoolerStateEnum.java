package io.github.hapjava.characteristics.impl.heatercooler;

import io.github.hapjava.characteristics.CharacteristicEnum;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

/** 0 ”Inactive” 1 ”Idle” 2 ”Heating” 3 ”Cooling” */
public enum CurrentHeaterCoolerStateEnum implements CharacteristicEnum {
  INACTIVE(0),
  IDLE(1),
  HEATING(2),
  COOLING(3);

  private static final Map<Integer, CurrentHeaterCoolerStateEnum> reverse;

  static {
    reverse =
        Arrays.stream(CurrentHeaterCoolerStateEnum.values())
            .collect(Collectors.toMap(CurrentHeaterCoolerStateEnum::getCode, t -> t));
  }

  public static CurrentHeaterCoolerStateEnum fromCode(Integer code) {
    return reverse.get(code);
  }

  private final int code;

  CurrentHeaterCoolerStateEnum(int code) {
    this.code = code;
  }

  @Override
  public int getCode() {
    return code;
  }
}
