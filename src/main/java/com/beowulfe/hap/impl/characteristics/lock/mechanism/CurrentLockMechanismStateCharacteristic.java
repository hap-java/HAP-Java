package com.beowulfe.hap.impl.characteristics.lock.mechanism;

import java.util.concurrent.CompletableFuture;

import com.beowulfe.hap.HomekitCharacteristicChangeCallback;
import com.beowulfe.hap.accessories.LockMechanism;
import com.beowulfe.hap.characteristics.EnumCharacteristic;
import com.beowulfe.hap.characteristics.EventableCharacteristic;

public class CurrentLockMechanismStateCharacteristic extends EnumCharacteristic implements EventableCharacteristic {

	private final LockMechanism lock;
	
	public CurrentLockMechanismStateCharacteristic(LockMechanism lock) {
		super("0000001D-0000-1000-8000-0026BB765291", false, true, "Current lock state", 3);
		this.lock = lock;
	}

	@Override
	protected void setValue(Integer value) throws Exception {
		//Not writable
	}

	@Override
	protected CompletableFuture<Integer> getValue() {
		return lock.getCurrentMechanismState().thenApply(s -> s.getCode());
	}

	@Override
	public void subscribe(HomekitCharacteristicChangeCallback callback) {
		lock.subscribeCurrentMechanismState(callback);
	}

	@Override
	public void unsubscribe() {
		lock.unsubscribeCurrentMechanismState();
	}

}
