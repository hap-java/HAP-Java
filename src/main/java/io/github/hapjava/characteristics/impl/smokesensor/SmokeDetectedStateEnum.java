package io.github.hapjava.characteristics.impl.smokesensor;

import io.github.hapjava.characteristics.CharacteristicEnum;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

/** 0 ”Smoke is not detected” 1 ”Smoke is detected” */
public enum SmokeDetectedStateEnum implements CharacteristicEnum {
  NOT_DETECTED(0),
  DETECTED(1);

  private static final Map<Integer, SmokeDetectedStateEnum> reverse;

  static {
    reverse =
        Arrays.stream(SmokeDetectedStateEnum.values())
            .collect(Collectors.toMap(SmokeDetectedStateEnum::getCode, t -> t));
  }

  public static SmokeDetectedStateEnum fromCode(Integer code) {
    return reverse.get(code);
  }

  private final int code;

  SmokeDetectedStateEnum(int code) {
    this.code = code;
  }

  public int getCode() {
    return code;
  }
}
