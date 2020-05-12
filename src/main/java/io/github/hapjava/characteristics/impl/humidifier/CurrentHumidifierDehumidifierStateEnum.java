package io.github.hapjava.characteristics.impl.humidifier;

import io.github.hapjava.characteristics.CharacteristicEnum;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

/** 0 ”Inactive” 1 ”Idle” 2 ”Humidifying” 3 ”Dehumidifying” */
public enum CurrentHumidifierDehumidifierStateEnum implements CharacteristicEnum {
  INACTIVE(0),
  IDLE(1),
  HUMIDIFYING(2),
  DEHUMIDIFYING(3);

  private static final Map<Integer, CurrentHumidifierDehumidifierStateEnum> reverse;

  static {
    reverse =
        Arrays.stream(CurrentHumidifierDehumidifierStateEnum.values())
            .collect(Collectors.toMap(CurrentHumidifierDehumidifierStateEnum::getCode, t -> t));
  }

  public static CurrentHumidifierDehumidifierStateEnum fromCode(Integer code) {
    return reverse.get(code);
  }

  private final int code;

  CurrentHumidifierDehumidifierStateEnum(int code) {
    this.code = code;
  }

  @Override
  public int getCode() {
    return code;
  }
}
