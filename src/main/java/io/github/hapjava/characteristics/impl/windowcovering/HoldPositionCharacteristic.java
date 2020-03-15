package io.github.hapjava.characteristics.impl.windowcovering;

import io.github.hapjava.characteristics.ExceptionalConsumer;
import io.github.hapjava.characteristics.impl.base.BooleanCharacteristic;
import java.util.Optional;

/**
 * This characteristic causes the service such as door or window covering to stop at its current
 * position. A value of 1 must hold the state of the accessory. For e.g, the window must stop moving
 * when this characteristic is written a value of 1. A value of 0 should be ignored.
 */
public class HoldPositionCharacteristic extends BooleanCharacteristic {

  public HoldPositionCharacteristic(ExceptionalConsumer<Boolean> setter) {
    super(
        "0000006F-0000-1000-8000-0026BB765291",
        "Hold Position",
        Optional.empty(),
        Optional.of(setter),
        Optional.empty(),
        Optional.empty());
  }
}
