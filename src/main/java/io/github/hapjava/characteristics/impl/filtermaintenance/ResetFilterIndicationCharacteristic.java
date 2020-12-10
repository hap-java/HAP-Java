package io.github.hapjava.characteristics.impl.filtermaintenance;

import io.github.hapjava.characteristics.ExceptionalConsumer;
import io.github.hapjava.characteristics.impl.base.IntegerCharacteristic;
import java.util.Optional;

public class ResetFilterIndicationCharacteristic extends IntegerCharacteristic {

  public ResetFilterIndicationCharacteristic(ExceptionalConsumer<Integer> setter) {
    super(
        "000000AD-0000-1000-8000-0026BB765291",
        "Reset Filter Indication",
        1,
        1,
        null,
        Optional.empty(),
        Optional.of(setter),
        Optional.empty(),
        Optional.empty());
  }
}
