package io.github.hapjava.characteristics.impl.fan;

import io.github.hapjava.characteristics.CharacteristicEnum;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

/** 0 ”Control lock disabled” 1 ”Control lock enabled” */
public enum LockPhysicalControlsEnum implements CharacteristicEnum {
  CONTROL_LOCK_DISABLED(0),
  CONTROL_LOCK_ENABLED(1);

  private static final Map<Integer, LockPhysicalControlsEnum> reverse;

  static {
    reverse =
        Arrays.stream(LockPhysicalControlsEnum.values())
            .collect(Collectors.toMap(LockPhysicalControlsEnum::getCode, t -> t));
  }

  public static LockPhysicalControlsEnum fromCode(Integer code) {
    return reverse.get(code);
  }

  private final int code;

  LockPhysicalControlsEnum(int code) {
    this.code = code;
  }

  @Override
  public int getCode() {
    return code;
  }
}
