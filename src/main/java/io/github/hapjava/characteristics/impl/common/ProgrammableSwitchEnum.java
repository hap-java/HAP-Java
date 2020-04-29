package io.github.hapjava.characteristics.impl.common;

import io.github.hapjava.characteristics.CharacteristicEnum;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

/** 0 ”Single Press” 1 ”Double Press” 2 ”Long Press” 3-255 ”Reserved” */
public enum ProgrammableSwitchEnum implements CharacteristicEnum {
  SINGLE_PRESS(0),
  DOUBLE_PRESS(1),
  LONG_PRESS(3);

  private static final Map<Integer, ProgrammableSwitchEnum> reverse;

  static {
    reverse =
        Arrays.stream(ProgrammableSwitchEnum.values())
            .collect(Collectors.toMap(ProgrammableSwitchEnum::getCode, t -> t));
  }

  public static ProgrammableSwitchEnum fromCode(Integer code) {
    return reverse.get(code);
  }

  private final int code;

  ProgrammableSwitchEnum(int code) {
    this.code = code;
  }

  @Override
  public int getCode() {
    return code;
  }
}
