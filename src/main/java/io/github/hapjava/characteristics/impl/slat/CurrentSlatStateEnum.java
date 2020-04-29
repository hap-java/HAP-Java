package io.github.hapjava.characteristics.impl.slat;

import io.github.hapjava.characteristics.CharacteristicEnum;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

/** 0 ”Fixed” 1 ”Jammed” 2 ”Swinging” */
public enum CurrentSlatStateEnum implements CharacteristicEnum {
  FIXED(0),
  JAMMED(1),
  SWINGING(2);

  private static final Map<Integer, CurrentSlatStateEnum> reverse;

  static {
    reverse =
        Arrays.stream(CurrentSlatStateEnum.values())
            .collect(Collectors.toMap(CurrentSlatStateEnum::getCode, t -> t));
  }

  public static CurrentSlatStateEnum fromCode(Integer code) {
    return reverse.get(code);
  }

  private final int code;

  CurrentSlatStateEnum(int code) {
    this.code = code;
  }

  @Override
  public int getCode() {
    return code;
  }
}
