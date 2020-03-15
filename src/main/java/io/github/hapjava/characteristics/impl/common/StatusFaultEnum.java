package io.github.hapjava.characteristics.impl.common;

import io.github.hapjava.characteristics.CharacteristicEnum;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

/** 0 ”No Fault” 1 ”General Fault” */
public enum StatusFaultEnum implements CharacteristicEnum {
  NO_FAULT(0),
  GENERAL_FAULT(1);

  private static final Map<Integer, StatusFaultEnum> reverse;

  static {
    reverse =
        Arrays.stream(StatusFaultEnum.values())
            .collect(Collectors.toMap(StatusFaultEnum::getCode, t -> t));
  }

  public static StatusFaultEnum fromCode(Integer code) {
    return reverse.get(code);
  }

  private final int code;

  StatusFaultEnum(int code) {
    this.code = code;
  }

  @Override
  public int getCode() {
    return code;
  }
}
