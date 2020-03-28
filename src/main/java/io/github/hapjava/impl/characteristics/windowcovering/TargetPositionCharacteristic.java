package io.github.hapjava.impl.characteristics.windowcovering;

import java.util.concurrent.CompletableFuture;

import io.github.hapjava.HomekitCharacteristicChangeCallback;
import io.github.hapjava.accessories.Positionable;
import io.github.hapjava.characteristics.EventableCharacteristic;
import io.github.hapjava.characteristics.IntegerCharacteristic;

public class TargetPositionCharacteristic extends IntegerCharacteristic implements EventableCharacteristic {

    private final Positionable positionable;

    public TargetPositionCharacteristic(Positionable positionable) {
        super("0000007C-0000-1000-8000-0026BB765291", true, true, "The target position", 0, 100, "%");
        this.positionable = positionable;
    }

    @Override
    protected void setValue(Integer value) throws Exception {
        positionable.setTargetPosition(value);
    }

    @Override
    protected CompletableFuture<Integer> getValue() {
        return positionable.getTargetPosition();
    }

    @Override
    public void subscribe(HomekitCharacteristicChangeCallback callback) {
        positionable.subscribeTargetPosition(callback);
    }

    @Override
    public void unsubscribe() {
        positionable.unsubscribeTargetPosition();
    }
}
