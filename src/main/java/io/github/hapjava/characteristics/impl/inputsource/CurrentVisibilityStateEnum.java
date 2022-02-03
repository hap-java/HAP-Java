package io.github.hapjava.characteristics.impl.inputsource;

import io.github.hapjava.characteristics.CharacteristicEnum;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public enum CurrentVisibilityStateEnum implements CharacteristicEnum {
  SHOWN(0),
  HIDDEN(1),
  UNKNOWN_1(2),
  UNKNOWN_2(3);

  private static final Map<Integer, CurrentVisibilityStateEnum> reverse;

  static {
    reverse =
        Arrays.stream(CurrentVisibilityStateEnum.values())
            .collect(Collectors.toMap(CurrentVisibilityStateEnum::getCode, t -> t));
  }

  public static CurrentVisibilityStateEnum fromCode(Integer code) {
    return reverse.get(code);
  }

  private final int code;

  CurrentVisibilityStateEnum(int code) {
    this.code = code;
  }

  @Override
  public int getCode() {
    return code;
  }
}
