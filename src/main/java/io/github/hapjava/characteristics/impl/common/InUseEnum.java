package io.github.hapjava.characteristics.impl.common;

import io.github.hapjava.characteristics.CharacteristicEnum;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

/** 0 ”Not in use” 1 ”In use” 2-255 ”Reserved” */
public enum InUseEnum implements CharacteristicEnum {
  NOT_IN_USE(0),
  IN_USE(1);

  private static final Map<Integer, InUseEnum> reverse;

  static {
    reverse =
        Arrays.stream(InUseEnum.values()).collect(Collectors.toMap(InUseEnum::getCode, t -> t));
  }

  public static InUseEnum fromCode(Integer code) {
    return reverse.get(code);
  }

  private final int code;

  InUseEnum(int code) {
    this.code = code;
  }

  @Override
  public int getCode() {
    return code;
  }
}
