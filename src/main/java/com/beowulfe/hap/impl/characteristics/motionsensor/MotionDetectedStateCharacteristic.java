package com.beowulfe.hap.impl.characteristics.motionsensor;

import com.beowulfe.hap.HomekitCharacteristicChangeCallback;
import com.beowulfe.hap.accessories.MotionSensor;
import com.beowulfe.hap.characteristics.BooleanCharacteristic;
import com.beowulfe.hap.characteristics.EventableCharacteristic;

import java.util.concurrent.CompletableFuture;

public class MotionDetectedStateCharacteristic extends BooleanCharacteristic implements EventableCharacteristic {

    private final MotionSensor motionSensor;

    public MotionDetectedStateCharacteristic(MotionSensor motionSensor) {
        super("00000022-0000-1000-8000-0026BB765291", false, true, "Motion Detected");
        this.motionSensor = motionSensor;
    }

    @Override
    protected CompletableFuture<Boolean> getValue() {
        return motionSensor.getMotionDetected();
    }

    @Override
    protected void setValue(Boolean value) throws Exception {
        //Read Only
    }

    @Override
    public void subscribe(HomekitCharacteristicChangeCallback callback) {
        motionSensor.subscribeMotionDetected(callback);
    }

    @Override
    public void unsubscribe() {
        motionSensor.unsubscribeMotionDetected();
    }
}
