package io.github.hapjava.characteristics.impl.television;

import io.github.hapjava.characteristics.CharacteristicEnum;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

/** 0 ”Play” 1 ”Pause” 2 "Stop" 3 "Unknown" 4-255 ”Reserved” */
public enum CurrentMediaStateEnum implements CharacteristicEnum {
  PLAY(0),
  PAUSE(1),
  STOP(2),
  UNKNOWN(3);

  private static final Map<Integer, CurrentMediaStateEnum> reverse;

  static {
    reverse =
        Arrays.stream(CurrentMediaStateEnum.values())
            .collect(Collectors.toMap(CurrentMediaStateEnum::getCode, t -> t));
  }

  public static CurrentMediaStateEnum fromCode(Integer code) {
    return reverse.get(code);
  }

  private final int code;

  CurrentMediaStateEnum(int code) {
    this.code = code;
  }

  @Override
  public int getCode() {
    return code;
  }
}
