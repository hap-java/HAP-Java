package io.github.hapjava.characteristics.impl.fan;

import io.github.hapjava.characteristics.CharacteristicEnum;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public enum CurrentFanStateEnum implements CharacteristicEnum {
  INACTIVE(0),
  IDLE(1),
  BLOWING_AIR(2);

  private static final Map<Integer, CurrentFanStateEnum> reverse;

  static {
    reverse =
        Arrays.stream(CurrentFanStateEnum.values())
            .collect(Collectors.toMap(CurrentFanStateEnum::getCode, t -> t));
  }

  public static CurrentFanStateEnum fromCode(Integer code) {
    return reverse.get(code);
  }

  private final int code;

  CurrentFanStateEnum(int code) {
    this.code = code;
  }

  @Override
  public int getCode() {
    return code;
  }
}
