package io.github.hapjava.characteristics.impl.inputsource;

import io.github.hapjava.characteristics.CharacteristicEnum;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public enum InputSourceTypeEnum implements CharacteristicEnum {
  OTHER(0),
  HOME_SCREEN(1),
  TUNER(2),
  HDMI(3),
  COMPOSITE_VIDEO(4),
  S_VIDEO(5),
  COMPONENT_VIDEO(6),
  DVI(7),
  AIRPLAY(8),
  USB(9),
  APPLICATION(10);

  private static final Map<Integer, InputSourceTypeEnum> reverse;

  static {
    reverse =
        Arrays.stream(InputSourceTypeEnum.values())
            .collect(Collectors.toMap(InputSourceTypeEnum::getCode, t -> t));
  }

  public static InputSourceTypeEnum fromCode(Integer code) {
    return reverse.get(code);
  }

  private final int code;

  InputSourceTypeEnum(int code) {
    this.code = code;
  }

  @Override
  public int getCode() {
    return code;
  }
}
