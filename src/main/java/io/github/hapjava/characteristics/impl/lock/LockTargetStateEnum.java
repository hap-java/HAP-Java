package io.github.hapjava.characteristics.impl.lock;

import io.github.hapjava.characteristics.CharacteristicEnum;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

/** 0 ”Unsecured” 1 ”Secured” 4-255 ”Reserved” */
public enum LockTargetStateEnum implements CharacteristicEnum {
  UNSECURED(0),
  SECURED(1);

  private static final Map<Integer, LockTargetStateEnum> reverse;

  static {
    reverse =
        Arrays.stream(LockTargetStateEnum.values())
            .collect(Collectors.toMap(LockTargetStateEnum::getCode, t -> t));
  }

  public static LockTargetStateEnum fromCode(Integer code) {
    return reverse.get(code);
  }

  private final int code;

  LockTargetStateEnum(int code) {
    this.code = code;
  }

  @Override
  public int getCode() {
    return code;
  }
}
