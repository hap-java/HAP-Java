package io.github.hapjava.characteristics.impl.common;

import io.github.hapjava.characteristics.CharacteristicEnum;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

/** 0 ”Inactive” 1 ”Active” 2-255 ”Reserved” */
public enum ActiveEnum implements CharacteristicEnum {
  INACTIVE(0),
  ACTIVE(1);

  private static final Map<Integer, ActiveEnum> reverse;

  static {
    reverse =
        Arrays.stream(ActiveEnum.values()).collect(Collectors.toMap(ActiveEnum::getCode, t -> t));
  }

  public static ActiveEnum fromCode(Integer code) {
    return reverse.get(code);
  }

  private final int code;

  ActiveEnum(int code) {
    this.code = code;
  }

  @Override
  public int getCode() {
    return code;
  }
}
