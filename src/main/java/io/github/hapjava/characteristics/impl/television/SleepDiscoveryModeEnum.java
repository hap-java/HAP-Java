package io.github.hapjava.characteristics.impl.television;

import io.github.hapjava.characteristics.CharacteristicEnum;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

/** 0 ”Not discoverable” 1 ”Always discoverable” 2-255 ”Reserved” */
public enum SleepDiscoveryModeEnum implements CharacteristicEnum {
  NOT_DISCOVERABLE(0),
  ALWAYS_DISCOVERABLE(1);

  private static final Map<Integer, SleepDiscoveryModeEnum> reverse;

  static {
    reverse =
        Arrays.stream(SleepDiscoveryModeEnum.values())
            .collect(Collectors.toMap(SleepDiscoveryModeEnum::getCode, t -> t));
  }

  public static SleepDiscoveryModeEnum fromCode(Integer code) {
    return reverse.get(code);
  }

  private final int code;

  SleepDiscoveryModeEnum(int code) {
    this.code = code;
  }

  @Override
  public int getCode() {
    return code;
  }
}
