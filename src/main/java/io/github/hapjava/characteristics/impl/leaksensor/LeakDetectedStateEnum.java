package io.github.hapjava.characteristics.impl.leaksensor;

import io.github.hapjava.characteristics.CharacteristicEnum;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

/** 0 ”Leak is not detected” 1 ”Leak is detected” */
public enum LeakDetectedStateEnum implements CharacteristicEnum {
  LEAK_NOT_DETECTED(0),
  LEAK_DETECTED(1);

  private static final Map<Integer, LeakDetectedStateEnum> reverse;

  static {
    reverse =
        Arrays.stream(LeakDetectedStateEnum.values())
            .collect(Collectors.toMap(LeakDetectedStateEnum::getCode, t -> t));
  }

  public static LeakDetectedStateEnum fromCode(Integer code) {
    return reverse.get(code);
  }

  private final int code;

  LeakDetectedStateEnum(int code) {
    this.code = code;
  }

  @Override
  public int getCode() {
    return code;
  }
}
