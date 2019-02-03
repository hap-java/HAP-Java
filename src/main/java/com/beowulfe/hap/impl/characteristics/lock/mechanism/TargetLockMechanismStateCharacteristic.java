package com.beowulfe.hap.impl.characteristics.lock.mechanism;

import com.beowulfe.hap.HomekitCharacteristicChangeCallback;
import com.beowulfe.hap.accessories.LockableLockMechanism;
import com.beowulfe.hap.accessories.properties.LockMechanismState;
import com.beowulfe.hap.characteristics.EnumCharacteristic;
import com.beowulfe.hap.characteristics.EventableCharacteristic;
import java.util.concurrent.CompletableFuture;

public class TargetLockMechanismStateCharacteristic extends EnumCharacteristic
    implements EventableCharacteristic {

  private final LockableLockMechanism lock;

  public TargetLockMechanismStateCharacteristic(LockableLockMechanism lock) {
    super("0000001E-0000-1000-8000-0026BB765291", true, true, "Current lock state", 3);
    this.lock = lock;
  }

  @Override
  protected void setValue(Integer value) throws Exception {
    lock.setTargetMechanismState(LockMechanismState.fromCode(value));
  }

  @Override
  protected CompletableFuture<Integer> getValue() {
    return lock.getTargetMechanismState().thenApply(s -> s.getCode());
  }

  @Override
  public void subscribe(HomekitCharacteristicChangeCallback callback) {
    lock.subscribeTargetMechanismState(callback);
  }

  @Override
  public void unsubscribe() {
    lock.unsubscribeTargetMechanismState();
  }
}
