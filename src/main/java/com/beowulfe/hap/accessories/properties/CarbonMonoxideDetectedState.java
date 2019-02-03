package com.beowulfe.hap.accessories.properties;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public enum CarbonMonoxideDetectedState {
  NORMAL(0),
  ABNORMAL(1);

  private static final Map<Integer, CarbonMonoxideDetectedState> reverse;

  static {
    reverse =
        Arrays.stream(CarbonMonoxideDetectedState.values())
            .collect(Collectors.toMap(CarbonMonoxideDetectedState::getCode, t -> t));
  }

  public static CarbonMonoxideDetectedState fromCode(Integer code) {
    return reverse.get(code);
  }

  private final int code;

  CarbonMonoxideDetectedState(int code) {
    this.code = code;
  }

  public int getCode() {
    return code;
  }
}
