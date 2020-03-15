package io.github.hapjava.characteristics.impl.fan;

import io.github.hapjava.characteristics.CharacteristicEnum;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

/** 0 ”Manual” 1 ”Auto” */
public enum TargetFanStateEnum implements CharacteristicEnum {
  MANUAL(0),
  AUTO(1);

  private static final Map<Integer, TargetFanStateEnum> reverse;

  static {
    reverse =
        Arrays.stream(TargetFanStateEnum.values())
            .collect(Collectors.toMap(TargetFanStateEnum::getCode, t -> t));
  }

  public static TargetFanStateEnum fromCode(Integer code) {
    return reverse.get(code);
  }

  private final int code;

  TargetFanStateEnum(int code) {
    this.code = code;
  }

  @Override
  public int getCode() {
    return code;
  }
}
