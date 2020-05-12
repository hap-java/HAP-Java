package io.github.hapjava.characteristics.impl.heatercooler;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;
import io.github.hapjava.characteristics.CharacteristicEnum;

/** 0 AUTO - ”Heat or Cool”
 1 ”Heat”
 2 ”Cool”
 */
public enum TargetHeaterCoolerStateEnum implements CharacteristicEnum {
  AUTO(0),
  HEAT(1),
  COOL(2);

  private static final Map<Integer, TargetHeaterCoolerStateEnum> reverse;

  static {
    reverse =
        Arrays.stream(TargetHeaterCoolerStateEnum.values())
            .collect(Collectors.toMap(TargetHeaterCoolerStateEnum::getCode, t -> t));
  }

  public static TargetHeaterCoolerStateEnum fromCode(Integer code) {
    return reverse.get(code);
  }

  private final int code;

  TargetHeaterCoolerStateEnum(int code) {
    this.code = code;
  }

  @Override
  public int getCode() {
    return code;
  }
}
