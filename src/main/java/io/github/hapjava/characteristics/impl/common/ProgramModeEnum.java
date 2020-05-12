package io.github.hapjava.characteristics.impl.common;

import io.github.hapjava.characteristics.CharacteristicEnum;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 0 ”No Programs Scheduled” 1 ”Program Scheduled” 2 ”Program Scheduled, currently overriden to
 * manual mode”
 */
public enum ProgramModeEnum implements CharacteristicEnum {
  NO_SCHEDULED(0),
  SCHEDULED(1),
  SCHEDULED_MANUAL(2);

  private static final Map<Integer, ProgramModeEnum> reverse;

  static {
    reverse =
        Arrays.stream(ProgramModeEnum.values())
            .collect(Collectors.toMap(ProgramModeEnum::getCode, t -> t));
  }

  public static ProgramModeEnum fromCode(Integer code) {
    return reverse.get(code);
  }

  private final int code;

  ProgramModeEnum(int code) {
    this.code = code;
  }

  @Override
  public int getCode() {
    return code;
  }
}
