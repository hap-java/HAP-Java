package io.github.hapjava.characteristics.impl.fan;

import io.github.hapjava.characteristics.CharacteristicEnum;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

/** 0 ”Clockwise” 1 ”Counter-clockwise” 2-255 ”Reserved” */
public enum RotationDirectionEnum implements CharacteristicEnum {
  CLOCKWISE(0),
  COUNTER_CLOCKWISE(1);

  private static final Map<Integer, RotationDirectionEnum> reverse;

  static {
    reverse =
        Arrays.stream(RotationDirectionEnum.values())
            .collect(Collectors.toMap(RotationDirectionEnum::getCode, t -> t));
  }

  public static RotationDirectionEnum fromCode(Integer code) {
    return reverse.get(code);
  }

  private final int code;

  RotationDirectionEnum(int code) {
    this.code = code;
  }

  @Override
  public int getCode() {
    return code;
  }
}
