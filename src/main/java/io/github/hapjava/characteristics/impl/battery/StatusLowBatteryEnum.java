package io.github.hapjava.characteristics.impl.battery;

import io.github.hapjava.characteristics.CharacteristicEnum;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

/** 0 ”Battery level is normal” 1 ”Battery level is low” */
public enum StatusLowBatteryEnum implements CharacteristicEnum {
  NORMAL(0),
  LOW(1);

  private static final Map<Integer, StatusLowBatteryEnum> reverse;

  static {
    reverse =
        Arrays.stream(StatusLowBatteryEnum.values())
            .collect(Collectors.toMap(StatusLowBatteryEnum::getCode, t -> t));
  }

  public static StatusLowBatteryEnum fromCode(Integer code) {
    return reverse.get(code);
  }

  private final int code;

  StatusLowBatteryEnum(int code) {
    this.code = code;
  }

  @Override
  public int getCode() {
    return code;
  }
}
