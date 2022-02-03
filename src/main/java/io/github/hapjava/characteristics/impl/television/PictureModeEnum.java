package io.github.hapjava.characteristics.impl.television;

import io.github.hapjava.characteristics.CharacteristicEnum;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 0 ”Other” 1 ”Standard” 2 "Calibrated" 3 "CalibratedDark" 4 "Vivid" 5 "Game" 6 "Computer" 7
 * "Custom" 8-255 ”Reserved”
 */
public enum PictureModeEnum implements CharacteristicEnum {
  OTHER(0),
  STANDARD(1),
  CALIBRATED(2),
  CALIBRATED_DARK(3),
  VIVID(4),
  GAME(5),
  COMPUTER(6),
  CUSTOM(7);

  private static final Map<Integer, PictureModeEnum> reverse;

  static {
    reverse =
        Arrays.stream(PictureModeEnum.values())
            .collect(Collectors.toMap(PictureModeEnum::getCode, t -> t));
  }

  public static PictureModeEnum fromCode(Integer code) {
    return reverse.get(code);
  }

  private final int code;

  PictureModeEnum(int code) {
    this.code = code;
  }

  @Override
  public int getCode() {
    return code;
  }
}
