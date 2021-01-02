package io.github.hapjava.characteristics.impl.garagedoor;

import io.github.hapjava.characteristics.CharacteristicEnum;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 0 ”Open. The door is fully open.” 1 ”Closed. The door is fully closed.” 2 ”Opening. The door is
 * actively opening.” 3 ”Closing. The door is actively closing.” 4 ”Stopped. The door is not moving,
 * and it is not fully open nor fully closed.”
 */
public enum CurrentDoorStateEnum implements CharacteristicEnum {
  OPEN(0),
  CLOSED(1),
  OPENING(2),
  CLOSING(3),
  STOPPED(4);

  private static final Map<Integer, CurrentDoorStateEnum> reverse;

  static {
    reverse =
        Arrays.stream(CurrentDoorStateEnum.values())
            .collect(Collectors.toMap(CurrentDoorStateEnum::getCode, t -> t));
  }

  public static CurrentDoorStateEnum fromCode(Integer code) {
    return reverse.get(code);
  }

  private final int code;

  CurrentDoorStateEnum(int code) {
    this.code = code;
  }

  @Override
  public int getCode() {
    return code;
  }
}
