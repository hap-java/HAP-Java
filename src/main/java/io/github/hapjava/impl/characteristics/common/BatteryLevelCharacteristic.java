package io.github.hapjava.impl.characteristics.common;

import io.github.hapjava.HomekitCharacteristicChangeCallback;
import io.github.hapjava.characteristics.EventableCharacteristic;
import io.github.hapjava.characteristics.IntegerCharacteristic;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * This characteristic is used by a stand-alone BatteryService, which describes a stand-alone
 * battery device, not the battery status of a battery operated device such as a motion sensor.
 */
public class BatteryLevelCharacteristic extends IntegerCharacteristic
    implements EventableCharacteristic {

  private final Supplier<CompletableFuture<Integer>> getter;
  private final Consumer<HomekitCharacteristicChangeCallback> subscriber;
  private final Runnable unsubscriber;

  public BatteryLevelCharacteristic(
      Supplier<CompletableFuture<Integer>> getter,
      Consumer<HomekitCharacteristicChangeCallback> subscriber,
      Runnable unsubscriber) {
    super("00000068-0000-1000-8000-0026BB765291", false, true, "Battery Level", 0, 100, "%");
    this.getter = getter;
    this.subscriber = subscriber;
    this.unsubscriber = unsubscriber;
  }

  @Override
  protected CompletableFuture<Integer> getValue() {
    return getter.get();
  }

  @Override
  protected void setValue(Integer value) throws Exception {
    // Read Only
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
