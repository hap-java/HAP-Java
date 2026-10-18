package io.github.hapjava.characteristics.impl.television;

import io.github.hapjava.characteristics.ExceptionalConsumer;
import io.github.hapjava.characteristics.impl.base.EnumCharacteristic;
import java.util.Optional;

/** characteristic sets power mode. See {@link PowerModeEnum} for possible values. */
public class PowerModeCharacteristic extends EnumCharacteristic<PowerModeEnum> {

  public PowerModeCharacteristic(ExceptionalConsumer<PowerModeEnum> setter) {
    this(PowerModeEnum.values(), setter);
  }

  public PowerModeCharacteristic(
      PowerModeEnum[] validValues, ExceptionalConsumer<PowerModeEnum> setter) {
    super(
        "000000DF-0000-1000-8000-0026BB765291",
        "power mode selection",
        validValues,
        Optional.empty(),
        Optional.of(setter),
        Optional.empty(),
        Optional.empty());
  }
}
