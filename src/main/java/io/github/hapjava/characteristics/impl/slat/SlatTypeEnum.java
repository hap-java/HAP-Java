package io.github.hapjava.characteristics.impl.slat;

import io.github.hapjava.characteristics.CharacteristicEnum;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

/** 0 ”Horizontal” 1 ”Vertical” */
public enum SlatTypeEnum implements CharacteristicEnum {
  HORIZONTAL(0),
  VERTICAL(1);

  private static final Map<Integer, SlatTypeEnum> reverse;

  static {
    reverse =
        Arrays.stream(SlatTypeEnum.values())
            .collect(Collectors.toMap(SlatTypeEnum::getCode, t -> t));
  }

  public static SlatTypeEnum fromCode(Integer code) {
    return reverse.get(code);
  }

  private final int code;

  SlatTypeEnum(int code) {
    this.code = code;
  }

  @Override
  public int getCode() {
    return code;
  }
}
