package io.github.hapjava.characteristics.impl.common;

import io.github.hapjava.characteristics.CharacteristicEnum;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

/** 0 ”Dots. For e.g ”.” ”..” ”...” ”....”” 1 ”Arabic numerals. For e.g. 0,1,2,3” */
public enum ServiceLabelNamespaceEnum implements CharacteristicEnum {
  DOTS(0),
  ARABIC_NUMERALS(1);

  private static final Map<Integer, ServiceLabelNamespaceEnum> reverse;

  static {
    reverse =
        Arrays.stream(ServiceLabelNamespaceEnum.values())
            .collect(Collectors.toMap(ServiceLabelNamespaceEnum::getCode, t -> t));
  }

  public static ServiceLabelNamespaceEnum fromCode(Integer code) {
    return reverse.get(code);
  }

  private final int code;

  ServiceLabelNamespaceEnum(int code) {
    this.code = code;
  }

  @Override
  public int getCode() {
    return code;
  }
}
