package com.beowulfe.hap.impl.services;

import com.beowulfe.hap.accessories.LockMechanism;
import com.beowulfe.hap.accessories.LockableLockMechanism;
import com.beowulfe.hap.impl.characteristics.lock.mechanism.CurrentLockMechanismStateCharacteristic;
import com.beowulfe.hap.impl.characteristics.lock.mechanism.TargetLockMechanismStateCharacteristic;

public class LockMechanismService extends AbstractServiceImpl {

	public LockMechanismService(LockMechanism lock) {
		super("00000045-0000-1000-8000-0026BB765291", lock);
		addCharacteristic(new CurrentLockMechanismStateCharacteristic(lock));
		
		if (lock instanceof LockableLockMechanism) {
			addCharacteristic(new TargetLockMechanismStateCharacteristic((LockableLockMechanism) lock));
		}
	}
}
