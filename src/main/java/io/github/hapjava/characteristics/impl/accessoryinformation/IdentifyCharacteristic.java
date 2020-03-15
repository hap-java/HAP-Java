package io.github.hapjava.characteristics.impl.accessoryinformation;

import io.github.hapjava.characteristics.impl.base.BooleanCharacteristic;
import io.github.hapjava.characteristics.ExceptionalConsumer;
import java.util.Optional;

/** This characteristic enables accessory to run its identify routine. */
public class IdentifyCharacteristic extends BooleanCharacteristic {

  public IdentifyCharacteristic(ExceptionalConsumer<Boolean> setter) {
    super(
        "00000014-0000-1000-8000-0026BB765291",
        "identifies the accessory via a physical action on the accessory",
        Optional.empty(),
        Optional.of(setter),
        Optional.empty(),
        Optional.empty());
  }
}
