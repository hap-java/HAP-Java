package io.github.hapjava.characteristics.impl.lock;

import io.github.hapjava.characteristics.CharacteristicEnum;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

/** 0 ”Unsecured” 1 ”Secured” 2 ”Jammed” 3 ”Unknown” 4-255 ”Reserved” */
public enum LockCurrentStateEnum implements CharacteristicEnum {
  UNSECURED(0),
  SECURED(1),
  JAMMED(2),
  UNKNOWN(3);

  private static final Map<Integer, LockCurrentStateEnum> reverse;

  static {
    reverse =
        Arrays.stream(LockCurrentStateEnum.values())
            .collect(Collectors.toMap(LockCurrentStateEnum::getCode, t -> t));
  }

  public static LockCurrentStateEnum fromCode(Integer code) {
    return reverse.get(code);
  }

  private final int code;

  LockCurrentStateEnum(int code) {
    this.code = code;
  }

  @Override
  public int getCode() {
    return code;
  }
}
