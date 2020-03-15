package io.github.hapjava.characteristics.impl.battery;

import io.github.hapjava.characteristics.CharacteristicEnum;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

/** 0 ”Not Charging” 1 ”Charging” 2 ”Not Chargeable” */
public enum ChargingStateEnum implements CharacteristicEnum {
  NOT_CHARGING(0),
  CHARGING(1),
  NOT_CHARABLE(2);

  private static final Map<Integer, ChargingStateEnum> reverse;

  static {
    reverse =
        Arrays.stream(ChargingStateEnum.values())
            .collect(Collectors.toMap(ChargingStateEnum::getCode, t -> t));
  }

  public static ChargingStateEnum fromCode(Integer code) {
    return reverse.get(code);
  }

  private final int code;

  ChargingStateEnum(int code) {
    this.code = code;
  }

  @Override
  public int getCode() {
    return code;
  }
}
