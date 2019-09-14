package io.github.hapjava.impl.characteristics.securitysystem;

import io.github.hapjava.HomekitCharacteristicChangeCallback;
import io.github.hapjava.accessories.SecuritySystem;
import io.github.hapjava.accessories.properties.SecuritySystemAlarmType;
import io.github.hapjava.characteristics.EnumCharacteristic;
import io.github.hapjava.characteristics.EventableCharacteristic;
import java.util.concurrent.CompletableFuture;

public class SecuritySystemAlarmTypeCharacteristic extends EnumCharacteristic
    implements EventableCharacteristic {

  private final SecuritySystem securitySystem;

  public SecuritySystemAlarmTypeCharacteristic(SecuritySystem securitySystem) {
    super("0000008E-0000-1000-8000-0026BB765291", false, true, "Security system alarm type", 1);
    this.securitySystem = securitySystem;
  }

  @Override
  protected CompletableFuture<Integer> getValue() {
    return securitySystem.getAlarmTypeState().thenApply(SecuritySystemAlarmType::getCode);
  }

  @Override
  protected void setValue(Integer value) throws Exception {
    // Not writable
  }

  @Override
  public void subscribe(HomekitCharacteristicChangeCallback callback) {
    securitySystem.subscribeAlarmTypeState(callback);
  }

  @Override
  public void unsubscribe() {
    securitySystem.unsubscribeAlarmTypeState();
  }
}
