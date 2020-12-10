package io.github.hapjava.characteristics.impl.filtermaintenance;

import io.github.hapjava.characteristics.EventableCharacteristic;
import io.github.hapjava.characteristics.HomekitCharacteristicChangeCallback;
import io.github.hapjava.characteristics.impl.base.EnumCharacteristic;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class FilterChangeIndicationCharacteristic
    extends EnumCharacteristic<FilterChangeIndicationEnum> implements EventableCharacteristic {

  public FilterChangeIndicationCharacteristic(
      Supplier<CompletableFuture<FilterChangeIndicationEnum>> getter,
      Consumer<HomekitCharacteristicChangeCallback> subscriber,
      Runnable unsubscriber) {
    super(
        "000000AC-0000-1000-8000-0026BB765291",
        "filter change indication",
        FilterChangeIndicationEnum.values(),
        Optional.of(getter),
        Optional.empty(),
        Optional.of(subscriber),
        Optional.of(unsubscriber));
  }
}
