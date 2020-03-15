package io.github.hapjava.characteristics.impl.valve;

import io.github.hapjava.accessories.ValveAccessory;
import io.github.hapjava.characteristics.CharacteristicEnum;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * The mode used by a {@link ValveAccessory}
 *
 * <p>0 ”Generic valve” 1 ”Irrigation” 2 ”Shower head” 3 ”Water faucet” 4-255 ”Reserved”
 *
 * @author Tim Harper
 */
public enum ValveTypeEnum implements CharacteristicEnum {
  GENERIC(0),
  IRRIGATION(1),
  SHOWER(2),
  WATER_FAUCET(3);

  private static final Map<Integer, ValveTypeEnum> reverse;

  static {
    reverse =
        Arrays.stream(ValveTypeEnum.values()).collect(Collectors.toMap(t -> t.getCode(), t -> t));
  }

  public static ValveTypeEnum fromCode(Integer code) {
    return reverse.get(code);
  }

  private final int code;

  private ValveTypeEnum(int code) {
    this.code = code;
  }

  public int getCode() {
    return code;
  }
}
