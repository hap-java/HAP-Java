package io.github.hapjava.characteristics.impl.televisionspeaker;

import io.github.hapjava.characteristics.CharacteristicEnum;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public enum VolumeSelectorEnum implements CharacteristicEnum {
  INCREMENT(0),
  DECREMENT(1);

  private static final Map<Integer, VolumeSelectorEnum> reverse;

  static {
    reverse =
        Arrays.stream(VolumeSelectorEnum.values())
            .collect(Collectors.toMap(VolumeSelectorEnum::getCode, t -> t));
  }

  public static VolumeSelectorEnum fromCode(Integer code) {
    return reverse.get(code);
  }

  private final int code;

  VolumeSelectorEnum(int code) {
    this.code = code;
  }

  @Override
  public int getCode() {
    return code;
  }
}
