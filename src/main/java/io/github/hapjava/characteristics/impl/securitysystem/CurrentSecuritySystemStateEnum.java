package io.github.hapjava.characteristics.impl.securitysystem;

import io.github.hapjava.accessories.SecuritySystemAccessory;
import io.github.hapjava.characteristics.CharacteristicEnum;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * The current state of a {@link SecuritySystemAccessory}. Unlike {@link
 * TargetSecuritySystemStateEnum}, this enum includes a triggered state. 0 ”Stay Arm. The home is
 * occupied and the residents are active. e.g. morning or evenings” 1 ”Away Arm. The home is
 * unoccupied” 2 ”Night Arm. The home is occupied and the residents are sleeping” 3 ”Disarmed” 4
 * ”Alarm Triggered” 5-255 ”Reserved”
 *
 * @author Gaston Dombiak
 */
public enum CurrentSecuritySystemStateEnum implements CharacteristicEnum {

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

  private static final Map<Integer, CurrentSecuritySystemStateEnum> reverse;

  static {
    reverse =
        Arrays.stream(CurrentSecuritySystemStateEnum.values())
            .collect(Collectors.toMap(CurrentSecuritySystemStateEnum::getCode, t -> t));
  }

  public static CurrentSecuritySystemStateEnum fromCode(Integer code) {
    return reverse.get(code);
  }

  private final int code;

  CurrentSecuritySystemStateEnum(int code) {
    this.code = code;
  }

  public int getCode() {
    return code;
  }
}
