package io.github.hapjava.accessories.properties;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public enum CarbonDioxideDetectedState {
  NORMAL(0),
  ABNORMAL(1);

  private static final Map<Integer, CarbonDioxideDetectedState> reverse;

  static {
    reverse =
        Arrays.stream(CarbonDioxideDetectedState.values())
            .collect(Collectors.toMap(CarbonDioxideDetectedState::getCode, t -> t));
  }

  public static CarbonDioxideDetectedState fromCode(Integer code) {
    return reverse.get(code);
  }

  private final int code;

  CarbonDioxideDetectedState(int code) {
    this.code = code;
  }

  public int getCode() {
    return code;
  }
}
