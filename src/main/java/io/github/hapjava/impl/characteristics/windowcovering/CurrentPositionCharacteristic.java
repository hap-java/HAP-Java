package io.github.hapjava.impl.characteristics.windowcovering;

import java.util.concurrent.CompletableFuture;

import io.github.hapjava.HomekitCharacteristicChangeCallback;
import io.github.hapjava.accessories.Positionable;
import io.github.hapjava.characteristics.EventableCharacteristic;
import io.github.hapjava.characteristics.IntegerCharacteristic;

public class CurrentPositionCharacteristic extends IntegerCharacteristic implements EventableCharacteristic {

    private final Positionable positionable;

    public CurrentPositionCharacteristic(Positionable positionable) {
        super("0000006D-0000-1000-8000-0026BB765291", false, true, "The current position", 0, 100, "%");
        this.positionable = positionable;
    }

    @Override
    protected void setValue(Integer value) throws Exception {
        // Read Only
    }

    @Override
    protected CompletableFuture<Integer> getValue() {
        return positionable.getCurrentPosition();
    }

    @Override
    public void subscribe(HomekitCharacteristicChangeCallback callback) {
        positionable.subscribeCurrentPosition(callback);
    }

    @Override
    public void unsubscribe() {
        positionable.unsubscribeCurrentPosition();
    }
}
