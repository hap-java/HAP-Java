package io.github.hapjava.characteristics.impl.inputsource;

import io.github.hapjava.characteristics.CharacteristicEnum;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public enum InputDeviceTypeEnum implements CharacteristicEnum {
  OTHER(0),
  TV(1),
  RECORDING(2),
  TUNER(3),
  PLAYBACK(4),
  AUDIO_SYSTEM(5);

  private static final Map<Integer, InputDeviceTypeEnum> reverse;

  static {
    reverse =
        Arrays.stream(InputDeviceTypeEnum.values())
            .collect(Collectors.toMap(InputDeviceTypeEnum::getCode, t -> t));
  }

  public static InputDeviceTypeEnum fromCode(Integer code) {
    return reverse.get(code);
  }

  private final int code;

  InputDeviceTypeEnum(int code) {
    this.code = code;
  }

  @Override
  public int getCode() {
    return code;
  }
}
