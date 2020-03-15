package io.github.hapjava.characteristics.impl.carbondioxidesensor;

import io.github.hapjava.characteristics.CharacteristicEnum;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

/** 0 ”Carbon Dioxide levels are normal” 1 ”Carbon Dioxide levels are abnormal” */
public enum CarbonDioxideDetectedEnum implements CharacteristicEnum {
  NORMAL(0),
  ABNORMAL(1);

  private static final Map<Integer, CarbonDioxideDetectedEnum> reverse;

  static {
    reverse =
        Arrays.stream(CarbonDioxideDetectedEnum.values())
            .collect(Collectors.toMap(CarbonDioxideDetectedEnum::getCode, t -> t));
  }

  public static CarbonDioxideDetectedEnum fromCode(Integer code) {
    return reverse.get(code);
  }

  private final int code;

  CarbonDioxideDetectedEnum(int code) {
    this.code = code;
  }

  @Override
  public int getCode() {
    return code;
  }
}
