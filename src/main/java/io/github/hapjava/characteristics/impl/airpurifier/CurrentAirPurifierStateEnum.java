package io.github.hapjava.characteristics.impl.airpurifier;

import io.github.hapjava.characteristics.CharacteristicEnum;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

/** 0 ”Inactive” 1 ”Idle” 2 ”Purifying Air” */
public enum CurrentAirPurifierStateEnum implements CharacteristicEnum {
  INACTIVE(0),
  IDLE(1),
  PURIFYING_AIR(2);

  private static final Map<Integer, CurrentAirPurifierStateEnum> reverse;

  static {
    reverse =
        Arrays.stream(CurrentAirPurifierStateEnum.values())
            .collect(Collectors.toMap(CurrentAirPurifierStateEnum::getCode, t -> t));
  }

  public static CurrentAirPurifierStateEnum fromCode(Integer code) {
    return reverse.get(code);
  }

  private final int code;

  CurrentAirPurifierStateEnum(int code) {
    this.code = code;
  }

  @Override
  public int getCode() {
    return code;
  }
}
