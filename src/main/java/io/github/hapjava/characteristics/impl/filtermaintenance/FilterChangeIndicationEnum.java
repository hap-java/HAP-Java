package io.github.hapjava.characteristics.impl.filtermaintenance;

import io.github.hapjava.characteristics.CharacteristicEnum;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public enum FilterChangeIndicationEnum implements CharacteristicEnum {
  NO_CHANGE_NEEDED(0),
  CHANGE_NEEDED(1);

  private static final Map<Integer, FilterChangeIndicationEnum> reverse;

  static {
    reverse =
        Arrays.stream(FilterChangeIndicationEnum.values())
            .collect(Collectors.toMap(FilterChangeIndicationEnum::getCode, t -> t));
  }

  public static FilterChangeIndicationEnum fromCode(Integer code) {
    return reverse.get(code);
  }

  private final int code;

  FilterChangeIndicationEnum(int code) {
    this.code = code;
  }

  @Override
  public int getCode() {
    return code;
  }
}
