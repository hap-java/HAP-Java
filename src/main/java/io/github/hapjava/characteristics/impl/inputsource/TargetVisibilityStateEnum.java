package io.github.hapjava.characteristics.impl.inputsource;

import io.github.hapjava.characteristics.CharacteristicEnum;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public enum TargetVisibilityStateEnum implements CharacteristicEnum {
  SHOWN(0),
  HIDDEN(1);

  private static final Map<Integer, TargetVisibilityStateEnum> reverse;

  static {
    reverse =
        Arrays.stream(TargetVisibilityStateEnum.values())
            .collect(Collectors.toMap(TargetVisibilityStateEnum::getCode, t -> t));
  }

  public static TargetVisibilityStateEnum fromCode(Integer code) {
    return reverse.get(code);
  }

  private final int code;

  TargetVisibilityStateEnum(int code) {
    this.code = code;
  }

  @Override
  public int getCode() {
    return code;
  }
}
