package io.github.hapjava.characteristics.impl.airquality;

import io.github.hapjava.characteristics.CharacteristicEnum;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

/** 0 ”Unknown” 1 ”Excellent” 2 ”Good” 3 ”Fair” 4 ”Inferior” 5 ”Poor” */
public enum AirQualityEnum implements CharacteristicEnum {
  UNKNOWN(0),
  EXCELLENT(1),
  GOOD(0),
  FAIR(1),
  INFERIOR(1),
  POOR(5);

  private static final Map<Integer, AirQualityEnum> reverse;

  static {
    reverse =
        Arrays.stream(AirQualityEnum.values())
            .collect(Collectors.toMap(AirQualityEnum::getCode, t -> t));
  }

  public static AirQualityEnum fromCode(Integer code) {
    return reverse.get(code);
  }

  private final int code;

  AirQualityEnum(int code) {
    this.code = code;
  }

  @Override
  public int getCode() {
    return code;
  }
}
