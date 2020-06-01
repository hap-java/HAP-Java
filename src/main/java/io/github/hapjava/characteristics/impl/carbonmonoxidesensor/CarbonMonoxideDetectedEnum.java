package io.github.hapjava.characteristics.impl.carbonmonoxidesensor;

import io.github.hapjava.characteristics.CharacteristicEnum;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

/** 0 ”Carbon Monoxide levels are normal” 1 ”Carbon Monoxide levels are abnormal” */
public enum CarbonMonoxideDetectedEnum implements CharacteristicEnum {
  NORMAL(0),
  ABNORMAL(1);

  private static final Map<Integer, CarbonMonoxideDetectedEnum> reverse;

  static {
    reverse =
        Arrays.stream(CarbonMonoxideDetectedEnum.values())
            .collect(Collectors.toMap(CarbonMonoxideDetectedEnum::getCode, t -> t));
  }

  public static CarbonMonoxideDetectedEnum fromCode(Integer code) {
    return reverse.get(code);
  }

  private final int code;

  CarbonMonoxideDetectedEnum(int code) {
    this.code = code;
  }

  @Override
  public int getCode() {
    return code;
  }
}
