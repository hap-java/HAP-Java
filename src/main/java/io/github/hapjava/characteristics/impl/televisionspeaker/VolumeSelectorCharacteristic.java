package io.github.hapjava.characteristics.impl.televisionspeaker;

import io.github.hapjava.characteristics.ExceptionalConsumer;
import io.github.hapjava.characteristics.impl.base.EnumCharacteristic;
import java.util.Optional;

/**
 * This characteristic sends information about volume changes. See {@link VolumeSelectorEnum} for
 * possible values.
 */
public class VolumeSelectorCharacteristic extends EnumCharacteristic<VolumeSelectorEnum> {
  public VolumeSelectorCharacteristic(ExceptionalConsumer<VolumeSelectorEnum> setter) {
    super(
        "000000EA-0000-1000-8000-0026BB765291",
        "volume selector",
        VolumeSelectorEnum.values(),
        Optional.empty(),
        Optional.of(setter),
        Optional.empty(),
        Optional.empty());
  }
}
