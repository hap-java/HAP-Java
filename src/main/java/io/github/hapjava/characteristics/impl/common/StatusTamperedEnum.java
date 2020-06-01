package io.github.hapjava.characteristics.impl.common;

import io.github.hapjava.characteristics.CharacteristicEnum;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

/** 0 ”Accessory is not tampered” 1 ”Accessory is tampered with” */
public enum StatusTamperedEnum implements CharacteristicEnum {
  NOT_TAMPERED(0),
  TAMPERED(1);

  private static final Map<Integer, StatusTamperedEnum> reverse;

  static {
    reverse =
        Arrays.stream(StatusTamperedEnum.values())
            .collect(Collectors.toMap(StatusTamperedEnum::getCode, t -> t));
  }

  public static StatusTamperedEnum fromCode(Integer code) {
    return reverse.get(code);
  }

  private final int code;

  StatusTamperedEnum(int code) {
    this.code = code;
  }

  @Override
  public int getCode() {
    return code;
  }
}
