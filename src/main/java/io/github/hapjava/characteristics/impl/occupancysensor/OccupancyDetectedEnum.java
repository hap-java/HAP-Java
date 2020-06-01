package io.github.hapjava.characteristics.impl.occupancysensor;

import io.github.hapjava.characteristics.CharacteristicEnum;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

/** 0 ”Occupancy is not detected” 1 ”Occupancy is detected” */
public enum OccupancyDetectedEnum implements CharacteristicEnum {
  NOT_DETECTED(0),
  DETECTED(1);

  private static final Map<Integer, OccupancyDetectedEnum> reverse;

  static {
    reverse =
        Arrays.stream(OccupancyDetectedEnum.values())
            .collect(Collectors.toMap(OccupancyDetectedEnum::getCode, t -> t));
  }

  public static OccupancyDetectedEnum fromCode(Integer code) {
    return reverse.get(code);
  }

  private final int code;

  OccupancyDetectedEnum(int code) {
    this.code = code;
  }

  @Override
  public int getCode() {
    return code;
  }
}
