package com.beowulfe.hap.impl.characteristics.common;

import com.beowulfe.hap.HomekitCharacteristicChangeCallback;
import com.beowulfe.hap.characteristics.EventableCharacteristic;
import com.beowulfe.hap.characteristics.IntegerCharacteristic;

import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class BatteryLevelCharacteristic extends IntegerCharacteristic implements EventableCharacteristic {

    private final Supplier<CompletableFuture<Integer>> getter;
    private final Consumer<HomekitCharacteristicChangeCallback> subscriber;
    private final Runnable unsubscriber;

    public BatteryLevelCharacteristic(Supplier<CompletableFuture<Integer>> getter,
                                      Consumer<HomekitCharacteristicChangeCallback> subscriber, Runnable unsubscriber) {
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
        //Read Only
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
