package io.github.hapjava.accessories.properties;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public enum ContactState {
  DETECTED(0),
  NOT_DETECTED(1);

  private static final Map<Integer, ContactState> reverse;

  static {
    reverse =
        Arrays.stream(ContactState.values())
            .collect(Collectors.toMap(ContactState::getCode, t -> t));
  }

  public static ContactState fromCode(Integer code) {
    return reverse.get(code);
  }

  private final int code;

  ContactState(int code) {
    this.code = code;
  }

  public int getCode() {
    return code;
  }
}
