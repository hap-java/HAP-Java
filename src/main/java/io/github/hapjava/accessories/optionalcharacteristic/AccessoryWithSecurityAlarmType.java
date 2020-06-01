package io.github.hapjava.accessories.optionalcharacteristic;

import io.github.hapjava.characteristics.HomekitCharacteristicChangeCallback;
import io.github.hapjava.characteristics.impl.securitysystem.SecuritySystemAlarmTypeEnum;
import java.util.concurrent.CompletableFuture;

/** This characteristic describes the type of alarm triggered by a security system. */
public interface AccessoryWithSecurityAlarmType {

  /**
   * return alarm type See {@link SecuritySystemAlarmTypeEnum} for possible values
   *
   * @return a future with the value
   */
  CompletableFuture<SecuritySystemAlarmTypeEnum> getSecurityAlarmType();

  /**
   * Subscribes to changes in status alarm type
   *
   * @param callback the function when the alarm type changes
   */
  void subscribeSecurityAlarmType(HomekitCharacteristicChangeCallback callback);

  /** Unsubscribes from changes */
  void unsubscribeSecurityAlarmType();
}
