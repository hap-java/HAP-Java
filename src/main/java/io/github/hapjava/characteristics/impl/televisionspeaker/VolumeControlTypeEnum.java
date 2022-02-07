package io.github.hapjava.characteristics.impl.televisionspeaker;

import io.github.hapjava.characteristics.CharacteristicEnum;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public enum VolumeControlTypeEnum implements CharacteristicEnum {
  NONE(0),
  RELATIVE(1),
  RELATIVE_WITH_CURRENT(2),
  ABSOLUTE(3);

  private static final Map<Integer, VolumeControlTypeEnum> reverse;

  static {
    reverse =
        Arrays.stream(VolumeControlTypeEnum.values())
            .collect(Collectors.toMap(VolumeControlTypeEnum::getCode, t -> t));
  }

  public static VolumeControlTypeEnum fromCode(Integer code) {
    return reverse.get(code);
  }

  private final int code;

  VolumeControlTypeEnum(int code) {
    this.code = code;
  }

  @Override
  public int getCode() {
    return code;
  }
}
