package com.beowulfe.hap.impl.characteristics.common;

import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import java.util.function.Supplier;

import com.beowulfe.hap.HomekitCharacteristicChangeCallback;
import com.beowulfe.hap.characteristics.BooleanCharacteristic;
import com.beowulfe.hap.characteristics.EventableCharacteristic;

public class LowBatteryStatusCharacteristic extends BooleanCharacteristic implements EventableCharacteristic {

    private final Supplier<CompletableFuture<Boolean>> getter;
    private final Consumer<HomekitCharacteristicChangeCallback> subscriber;
    private final Runnable unsubscriber;

    public LowBatteryStatusCharacteristic(Supplier<CompletableFuture<Boolean>> getter,
            Consumer<HomekitCharacteristicChangeCallback> subscriber, Runnable unsubscriber) {
        super("00000079-0000-1000-8000-0026BB765291", false, true, "Status Low Battery");
        this.getter = getter;
        this.subscriber = subscriber;
        this.unsubscriber = unsubscriber;
    }

    @Override
    protected CompletableFuture<Boolean> getValue() {
        return getter.get();
    }

    @Override
    protected void setValue(Boolean value) throws Exception {
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
