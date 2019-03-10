package io.github.hapjava.impl.characteristics.securitysystem;

import io.github.hapjava.HomekitCharacteristicChangeCallback;
import io.github.hapjava.accessories.SecuritySystem;
import io.github.hapjava.accessories.properties.CurrentSecuritySystemState;
import io.github.hapjava.characteristics.EnumCharacteristic;
import io.github.hapjava.characteristics.EventableCharacteristic;
import java.util.concurrent.CompletableFuture;

public class CurrentSecuritySystemStateCharacteristic extends EnumCharacteristic
    implements EventableCharacteristic {

  private final SecuritySystem securitySystem;

  public CurrentSecuritySystemStateCharacteristic(SecuritySystem securitySystem) {
    super("00000066-0000-1000-8000-0026BB765291", false, true, "Current security system state", 4);
    this.securitySystem = securitySystem;
  }

  @Override
  protected CompletableFuture<Integer> getValue() {
    return securitySystem
        .getCurrentSecuritySystemState()
        .thenApply(CurrentSecuritySystemState::getCode);
  }

  @Override
  protected void setValue(Integer value) throws Exception {
    // Not writable
  }

  @Override
  public void subscribe(HomekitCharacteristicChangeCallback callback) {
    securitySystem.subscribeCurrentSecuritySystemState(callback);
  }

  @Override
  public void unsubscribe() {
    securitySystem.unsubscribeCurrentSecuritySystemState();
  }
}
