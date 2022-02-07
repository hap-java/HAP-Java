package io.github.hapjava.characteristics.impl.television;

import io.github.hapjava.characteristics.CharacteristicEnum;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

/** 0 ”Show” 1 ”Hide” 2-255 ”Reserved” */
public enum PowerModeEnum implements CharacteristicEnum {
  SHOW(0),
  HIDE(1);

  private static final Map<Integer, PowerModeEnum> reverse;

  static {
    reverse =
        Arrays.stream(PowerModeEnum.values())
            .collect(Collectors.toMap(PowerModeEnum::getCode, t -> t));
  }

  public static PowerModeEnum fromCode(Integer code) {
    return reverse.get(code);
  }

  private final int code;

  PowerModeEnum(int code) {
    this.code = code;
  }

  @Override
  public int getCode() {
    return code;
  }
}
