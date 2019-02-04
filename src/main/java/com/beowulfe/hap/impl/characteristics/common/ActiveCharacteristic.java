package com.beowulfe.hap.impl.characteristics.common;

import com.beowulfe.hap.HomekitCharacteristicChangeCallback;
import com.beowulfe.hap.characteristics.BooleanCharacteristic;
import com.beowulfe.hap.characteristics.EventableCharacteristic;
import com.beowulfe.hap.impl.ExceptionalConsumer;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class ActiveCharacteristic extends BooleanCharacteristic implements EventableCharacteristic {

  private final Supplier<CompletableFuture<Boolean>> getter;
  private final ExceptionalConsumer<Boolean> setter;
  private final Consumer<HomekitCharacteristicChangeCallback> subscriber;
  private final Runnable unsubscriber;

  public ActiveCharacteristic(
      Supplier<CompletableFuture<Boolean>> getter,
      ExceptionalConsumer<Boolean> setter,
      Consumer<HomekitCharacteristicChangeCallback> subscriber,
      Runnable unsubscriber) {
    super("000000B0-0000-1000-8000-0026BB765291", true, true, "Active");
    this.getter = getter;
    this.setter = setter;
    this.subscriber = subscriber;
    this.unsubscriber = unsubscriber;
  }

  @Override
  protected CompletableFuture<Boolean> getValue() {
    return getter.get();
  }

  @Override
  protected void setValue(Boolean value) throws Exception {
    setter.accept(value);
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
