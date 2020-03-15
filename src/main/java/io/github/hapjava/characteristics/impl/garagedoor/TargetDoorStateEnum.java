package io.github.hapjava.characteristics.impl.garagedoor;

import io.github.hapjava.characteristics.CharacteristicEnum;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

/** 0 ”Open” 1 ”Closed” 2-255 ”Reserved” */
public enum TargetDoorStateEnum implements CharacteristicEnum {
  OPEN(0),
  CLOSED(1);

  private static final Map<Integer, TargetDoorStateEnum> reverse;

  static {
    reverse =
        Arrays.stream(TargetDoorStateEnum.values())
            .collect(Collectors.toMap(TargetDoorStateEnum::getCode, t -> t));
  }

  public static TargetDoorStateEnum fromCode(Integer code) {
    return reverse.get(code);
  }

  private final int code;

  TargetDoorStateEnum(int code) {
    this.code = code;
  }

  @Override
  public int getCode() {
    return code;
  }
}
