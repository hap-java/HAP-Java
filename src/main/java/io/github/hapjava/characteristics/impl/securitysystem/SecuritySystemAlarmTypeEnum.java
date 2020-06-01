package io.github.hapjava.characteristics.impl.securitysystem;

import io.github.hapjava.accessories.SecuritySystemAccessory;
import io.github.hapjava.characteristics.CharacteristicEnum;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Type of alarm of a {@link SecuritySystemAccessory}.
 *
 * <p>This characteristic describes the type of alarm triggered by a security system. A value of 1
 * indicates an ʼunknownʼ cause. Value should revert to 0 when the alarm conditions are cleared.
 *
 * @author Gaston Dombiak
 */
public enum SecuritySystemAlarmTypeEnum implements CharacteristicEnum {

  /** Alarm conditions are cleared */
  NO_ALARM(0),
  /** Alarm type is not known */
  UNKNOWN(1);

  private static final Map<Integer, SecuritySystemAlarmTypeEnum> reverse;

  static {
    reverse =
        Arrays.stream(SecuritySystemAlarmTypeEnum.values())
            .collect(Collectors.toMap(SecuritySystemAlarmTypeEnum::getCode, t -> t));
  }

  public static SecuritySystemAlarmTypeEnum fromCode(Integer code) {
    return reverse.get(code);
  }

  private final int code;

  SecuritySystemAlarmTypeEnum(int code) {
    this.code = code;
  }

  public int getCode() {
    return code;
  }
}
