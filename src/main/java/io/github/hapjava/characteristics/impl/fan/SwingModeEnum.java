package io.github.hapjava.characteristics.impl.fan;

import io.github.hapjava.characteristics.CharacteristicEnum;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

/** 0 ”Swing disabled” 1 ”Swing enabled” */
public enum SwingModeEnum implements CharacteristicEnum {
  SWING_DISABLED(0),
  SWING_ENABLED(1);

  private static final Map<Integer, SwingModeEnum> reverse;

  static {
    reverse =
        Arrays.stream(SwingModeEnum.values())
            .collect(Collectors.toMap(SwingModeEnum::getCode, t -> t));
  }

  public static SwingModeEnum fromCode(Integer code) {
    return reverse.get(code);
  }

  private final int code;

  SwingModeEnum(int code) {
    this.code = code;
  }

  @Override
  public int getCode() {
    return code;
  }
}
