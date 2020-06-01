package io.github.hapjava.characteristics.impl.contactsensor;

import io.github.hapjava.characteristics.CharacteristicEnum;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

/*
 * 0 ”Contact is detected”
 * 1 ”Contact is not detected”
 */
public enum ContactStateEnum implements CharacteristicEnum {
  DETECTED(0),
  NOT_DETECTED(1);

  private static final Map<Integer, ContactStateEnum> reverse;

  static {
    reverse =
        Arrays.stream(ContactStateEnum.values())
            .collect(Collectors.toMap(ContactStateEnum::getCode, t -> t));
  }

  public static ContactStateEnum fromCode(Integer code) {
    return reverse.get(code);
  }

  private final int code;

  ContactStateEnum(int code) {
    this.code = code;
  }

  public int getCode() {
    return code;
  }
}
