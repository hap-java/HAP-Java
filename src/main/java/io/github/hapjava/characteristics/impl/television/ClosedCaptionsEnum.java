package io.github.hapjava.characteristics.impl.television;

import io.github.hapjava.characteristics.CharacteristicEnum;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

/** 0 ”Disabled” 1 ”Enabled” 2-255 ”Reserved” */
public enum ClosedCaptionsEnum implements CharacteristicEnum {
  DISABLED(0),
  ENABLED(1);

  private static final Map<Integer, ClosedCaptionsEnum> reverse;

  static {
    reverse =
        Arrays.stream(ClosedCaptionsEnum.values())
            .collect(Collectors.toMap(ClosedCaptionsEnum::getCode, t -> t));
  }

  public static ClosedCaptionsEnum fromCode(Integer code) {
    return reverse.get(code);
  }

  private final int code;

  ClosedCaptionsEnum(int code) {
    this.code = code;
  }

  @Override
  public int getCode() {
    return code;
  }
}
