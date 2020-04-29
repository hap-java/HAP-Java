package io.github.hapjava.characteristics.impl.audio;

import io.github.hapjava.characteristics.CharacteristicEnum;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

/** 0 ”Mute is Off / Audio is On” 1 ”Mute is On / There is no Audio” */
public enum MuteEnum implements CharacteristicEnum {
  OFF(0),
  ON(1);

  private static final Map<Integer, MuteEnum> reverse;

  static {
    reverse = Arrays.stream(MuteEnum.values()).collect(Collectors.toMap(MuteEnum::getCode, t -> t));
  }

  public static MuteEnum fromCode(Integer code) {
    return reverse.get(code);
  }

  private final int code;

  MuteEnum(int code) {
    this.code = code;
  }

  @Override
  public int getCode() {
    return code;
  }
}
