package io.github.hapjava.characteristics.impl.common;

import io.github.hapjava.characteristics.CharacteristicEnum;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

/** 0 ”Not Configured” 1 ”Configured” 2-255 ”Reserved” */
public enum IsConfiguredEnum implements CharacteristicEnum {
  NOT_CONFIGURED(0),
  CONFIGURED(1);

  private static final Map<Integer, IsConfiguredEnum> reverse;

  static {
    reverse =
        Arrays.stream(IsConfiguredEnum.values())
            .collect(Collectors.toMap(IsConfiguredEnum::getCode, t -> t));
  }

  public static IsConfiguredEnum fromCode(Integer code) {
    return reverse.get(code);
  }

  private final int code;

  IsConfiguredEnum(int code) {
    this.code = code;
  }

  @Override
  public int getCode() {
    return code;
  }
}
