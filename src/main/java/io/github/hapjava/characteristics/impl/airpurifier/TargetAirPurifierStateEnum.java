package io.github.hapjava.characteristics.impl.airpurifier;

import io.github.hapjava.characteristics.CharacteristicEnum;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

/** 0 ”Manual” 1 ”Auto” */
public enum TargetAirPurifierStateEnum implements CharacteristicEnum {
  MANUAL(0),
  AUTO(1);
  private static final Map<Integer, TargetAirPurifierStateEnum> reverse;

  static {
    reverse =
        Arrays.stream(TargetAirPurifierStateEnum.values())
            .collect(Collectors.toMap(t -> t.getCode(), t -> t));
  }

  public static TargetAirPurifierStateEnum fromCode(Integer code) {
    return reverse.get(code);
  }

  private final int code;

  private TargetAirPurifierStateEnum(int code) {
    this.code = code;
  }

  public int getCode() {
    return code;
  }
}
