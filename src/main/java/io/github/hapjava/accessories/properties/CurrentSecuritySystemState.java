package io.github.hapjava.accessories.properties;

import io.github.hapjava.accessories.SecuritySystem;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * The current state of a {@link SecuritySystem}. Unlike {@link TargetSecuritySystemState}, this
 * enum includes a triggered state.
 *
 * @author Gaston Dombiak
 */
public enum CurrentSecuritySystemState {

  /** The home is occupied and residents are active. */
  STAY_ARM(0),
  /** The home is unoccupied. */
  AWAY_ARM(1),
  /** The home is occupied and residents are sleeping. */
  NIGHT_ARM(2),
  /** The security system is disarmed. */
  DISARMED(3),
  /** The security system is triggered. */
  TRIGGERED(4);

  private static final Map<Integer, CurrentSecuritySystemState> reverse;

  static {
    reverse =
        Arrays.stream(CurrentSecuritySystemState.values())
            .collect(Collectors.toMap(CurrentSecuritySystemState::getCode, t -> t));
  }

  public static CurrentSecuritySystemState fromCode(Integer code) {
    return reverse.get(code);
  }

  private final int code;

  CurrentSecuritySystemState(int code) {
    this.code = code;
  }

  public int getCode() {
    return code;
  }
}
