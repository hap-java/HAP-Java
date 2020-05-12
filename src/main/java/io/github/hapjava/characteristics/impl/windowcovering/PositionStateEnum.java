package io.github.hapjava.characteristics.impl.windowcovering;

import io.github.hapjava.characteristics.CharacteristicEnum;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 0 ”Going to the minimum value specified in metadata” 1 ”Going to the maximum value specified in
 * metadata” 2 ”Stopped”
 */
public enum PositionStateEnum implements CharacteristicEnum {
  DECREASING(0),
  INCREASING(1),
  STOPPED(2);

  private static final Map<Integer, PositionStateEnum> reverse;

  static {
    reverse =
        Arrays.stream(PositionStateEnum.values())
            .collect(Collectors.toMap(PositionStateEnum::getCode, t -> t));
  }

  public static PositionStateEnum fromCode(Integer code) {
    return reverse.get(code);
  }

  private final int code;

  PositionStateEnum(int code) {
    this.code = code;
  }

  @Override
  public int getCode() {
    return code;
  }
}
