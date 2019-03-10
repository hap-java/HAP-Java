package io.github.hapjava.impl.characteristics.common;

import io.github.hapjava.HomekitCharacteristicChangeCallback;
import io.github.hapjava.characteristics.BooleanCharacteristic;
import io.github.hapjava.characteristics.EventableCharacteristic;
import io.github.hapjava.impl.ExceptionalConsumer;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class PowerStateCharacteristic extends BooleanCharacteristic
    implements EventableCharacteristic {

  private final Supplier<CompletableFuture<Boolean>> getter;
  private final ExceptionalConsumer<Boolean> setter;
  private final Consumer<HomekitCharacteristicChangeCallback> subscriber;
  private final Runnable unsubscriber;

  public PowerStateCharacteristic(
      Supplier<CompletableFuture<Boolean>> getter,
      ExceptionalConsumer<Boolean> setter,
      Consumer<HomekitCharacteristicChangeCallback> subscriber,
      Runnable unsubscriber) {
    super("00000025-0000-1000-8000-0026BB765291", true, true, "Turn on and off");
    this.getter = getter;
    this.setter = setter;
    this.subscriber = subscriber;
    this.unsubscriber = unsubscriber;
  }

  @Override
  public void setValue(Boolean value) throws Exception {
    setter.accept(value);
  }

  @Override
  protected CompletableFuture<Boolean> getValue() {
    return getter.get();
  }

  @Override
  public void subscribe(HomekitCharacteristicChangeCallback callback) {
    subscriber.accept(callback);
  }

  @Override
  public void unsubscribe() {
    unsubscriber.run();
  }
}
