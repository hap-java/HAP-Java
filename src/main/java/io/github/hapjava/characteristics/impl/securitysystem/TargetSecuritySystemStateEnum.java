package io.github.hapjava.characteristics.impl.securitysystem;

import io.github.hapjava.accessories.SecuritySystemAccessory;
import io.github.hapjava.characteristics.CharacteristicEnum;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * The target state of a {@link SecuritySystemAccessory}.
 *
 * <p>0 ”Stay Arm. The home is occupied and the residents are active. e.g. morning or evenings” 1
 * ”Away Arm. The home is unoccupied” 2 ”Night Arm. The home is occupied and the residents are
 * sleeping” 3 ”Disarm” 4-255 ”Reserved”
 *
 * @author Gaston Dombiak
 */
public enum TargetSecuritySystemStateEnum implements CharacteristicEnum {

  /** Arm the system when the home is occupied and residents are active. */
  STAY_ARM(0),
  /** Arm the system when the home is unoccupied. */
  AWAY_ARM(1),
  /** Arm the system when the home is occupied and residents are sleeping. */
  NIGHT_ARM(2),
  /** Disarm the system. */
  DISARM(3);

  private static final Map<Integer, TargetSecuritySystemStateEnum> reverse;

  static {
    reverse =
        Arrays.stream(TargetSecuritySystemStateEnum.values())
            .collect(Collectors.toMap(TargetSecuritySystemStateEnum::getCode, t -> t));
  }

  public static TargetSecuritySystemStateEnum fromCode(Integer code) {
    return reverse.get(code);
  }

  private final int code;

  TargetSecuritySystemStateEnum(int code) {
    this.code = code;
  }

  public int getCode() {
    return code;
  }
}
