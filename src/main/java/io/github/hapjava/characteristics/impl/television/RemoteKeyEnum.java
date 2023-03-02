package io.github.hapjava.characteristics.impl.television;

import io.github.hapjava.characteristics.CharacteristicEnum;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public enum RemoteKeyEnum implements CharacteristicEnum {
  REWIND(0),
  FAST_FORWARD(1),
  NEXT_TRACK(2),
  PREV_TRACK(3),
  ARROW_UP(4),
  ARROW_DOWN(5),
  ARROW_LEFT(6),
  ARROW_RIGHT(7),
  SELECT(8),
  BACK(9),
  EXIT(10),
  PLAY_PAUSE(11),
  INFO(15);

  private static final Map<Integer, RemoteKeyEnum> reverse;

  static {
    reverse =
        Arrays.stream(RemoteKeyEnum.values())
            .collect(Collectors.toMap(RemoteKeyEnum::getCode, t -> t));
  }

  public static RemoteKeyEnum fromCode(Integer code) {
    return reverse.get(code);
  }

  private final int code;

  RemoteKeyEnum(int code) {
    this.code = code;
  }

  @Override
  public int getCode() {
    return code;
  }
}
