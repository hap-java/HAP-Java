package io.github.hapjava.characteristics.impl.television;

import io.github.hapjava.characteristics.CharacteristicEnum;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

/** 0 ”Play” 1 ”Pause” 2 "Stop" 3-255 ”Reserved” */
public enum TargetMediaStateEnum implements CharacteristicEnum {
  PLAY(0),
  PAUSE(1),
  STOP(2);

  private static final Map<Integer, TargetMediaStateEnum> reverse;

  static {
    reverse =
        Arrays.stream(TargetMediaStateEnum.values())
            .collect(Collectors.toMap(TargetMediaStateEnum::getCode, t -> t));
  }

  public static TargetMediaStateEnum fromCode(Integer code) {
    return reverse.get(code);
  }

  private final int code;

  TargetMediaStateEnum(int code) {
    this.code = code;
  }

  @Override
  public int getCode() {
    return code;
  }
}
