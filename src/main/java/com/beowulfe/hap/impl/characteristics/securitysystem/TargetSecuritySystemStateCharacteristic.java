package com.beowulfe.hap.impl.characteristics.securitysystem;

import com.beowulfe.hap.HomekitCharacteristicChangeCallback;
import com.beowulfe.hap.accessories.SecuritySystem;
import com.beowulfe.hap.accessories.properties.TargetSecuritySystemState;
import com.beowulfe.hap.characteristics.EnumCharacteristic;
import com.beowulfe.hap.characteristics.EventableCharacteristic;

import java.util.concurrent.CompletableFuture;

public class TargetSecuritySystemStateCharacteristic extends EnumCharacteristic implements EventableCharacteristic {

    private final SecuritySystem securitySystem;

    public TargetSecuritySystemStateCharacteristic(SecuritySystem securitySystem) {
        super("00000067-0000-1000-8000-0026BB765291", true, true, "Target security system state", 3);
        this.securitySystem = securitySystem;
    }

    @Override
    protected CompletableFuture<Integer> getValue() {
        return securitySystem.getTargetSecuritySystemState().thenApply(TargetSecuritySystemState::getCode);
    }

    @Override
    protected void setValue(Integer value) throws Exception {
        securitySystem.setTargetSecuritySystemState(TargetSecuritySystemState.fromCode(value));
    }

    @Override
    public void subscribe(HomekitCharacteristicChangeCallback callback) {
        securitySystem.subscribeTargetSecuritySystemState(callback);
    }

    @Override
    public void unsubscribe() {
        securitySystem.unsubscribeTargetSecuritySystemState();
    }
}
