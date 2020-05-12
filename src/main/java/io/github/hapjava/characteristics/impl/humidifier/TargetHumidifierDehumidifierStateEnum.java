package io.github.hapjava.characteristics.impl.humidifier;

import io.github.hapjava.characteristics.CharacteristicEnum;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

/** 0 - Auto - ”Humidifier or Dehumidifier” 1 ”Humidifier” 2 ”Dehumidifier” */
public enum TargetHumidifierDehumidifierStateEnum implements CharacteristicEnum {
  AUTO(0),
  HUMIDIFIER(1),
  DEHUMIDIFIER(2);

  private static final Map<Integer, TargetHumidifierDehumidifierStateEnum> reverse;

  static {
    reverse =
        Arrays.stream(TargetHumidifierDehumidifierStateEnum.values())
            .collect(Collectors.toMap(TargetHumidifierDehumidifierStateEnum::getCode, t -> t));
  }

  public static TargetHumidifierDehumidifierStateEnum fromCode(Integer code) {
    return reverse.get(code);
  }

  private final int code;

  TargetHumidifierDehumidifierStateEnum(int code) {
    this.code = code;
  }

  @Override
  public int getCode() {
    return code;
  }
}
