package io.github.hapjava.characteristics.impl.accessoryinformation;

import io.github.hapjava.characteristics.CharacteristicEnum;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

/** 0x0001 (bit0) ”Requires additional setup” 0x0002 - 0xFFFF ”Reserved” */
public enum AccessoryFlagsEnum implements CharacteristicEnum {
  NO_FLAGS(0),
  REQUIRES_ADDITIONAL_SETUP(1);

  private static final Map<Integer, AccessoryFlagsEnum> reverse;

  static {
    reverse =
        Arrays.stream(AccessoryFlagsEnum.values())
            .collect(Collectors.toMap(AccessoryFlagsEnum::getCode, t -> t));
  }

  public static AccessoryFlagsEnum fromCode(Integer code) {
    return reverse.get(code);
  }

  private final int code;

  AccessoryFlagsEnum(int code) {
    this.code = code;
  }

  @Override
  public int getCode() {
    return code;
  }
}
