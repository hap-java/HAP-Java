package io.github.hapjava.impl.characteristics.windowcovering;

import java.util.concurrent.CompletableFuture;

import io.github.hapjava.HomekitCharacteristicChangeCallback;
import io.github.hapjava.accessories.Positionable;
import io.github.hapjava.characteristics.EnumCharacteristic;
import io.github.hapjava.characteristics.EventableCharacteristic;

public class PositionStateCharacteristic extends EnumCharacteristic implements EventableCharacteristic {

    private final Positionable positionable;

    public PositionStateCharacteristic(Positionable positionable) {
        super("00000072-0000-1000-8000-0026BB765291", false, true, "The position state", 2);
        this.positionable = positionable;
    }

    @Override
    protected void setValue(Integer value) throws Exception {
        // Read only
    }

    @Override
    protected CompletableFuture<Integer> getValue() {
        return positionable.getPositionState().thenApply(v -> v.getCode());
    }

    @Override
    public void subscribe(HomekitCharacteristicChangeCallback callback) {
        positionable.subscribePositionState(callback);
    }

    @Override
    public void unsubscribe() {
        positionable.unsubscribePositionState();
    }
}
