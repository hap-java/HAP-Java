package io.github.hapjava.characteristics.impl.windowcovering;

import io.github.hapjava.characteristics.ExceptionalConsumer;
import io.github.hapjava.characteristics.impl.base.BooleanCharacteristic;
import java.util.Optional;

/** This characteristic causes the accessories like window covering to stop at its current */
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
