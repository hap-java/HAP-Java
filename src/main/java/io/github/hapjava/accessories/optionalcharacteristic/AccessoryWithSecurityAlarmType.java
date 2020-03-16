package io.github.hapjava.accessories.optionalcharacteristic;

import io.github.hapjava.characteristics.HomekitCharacteristicChangeCallback;
import io.github.hapjava.characteristics.impl.securitysystem.SecuritySystemAlarmTypeEnum;
import java.util.concurrent.CompletableFuture;

/**
 * This characteristic describes the type of alarm triggered by a security system. A value of 1
 * indicates an ʼunknownʼ cause. Value should revert to 0 when the alarm conditions are cleared.
 *
 * @author Eugen Freiter
 */
public interface AccessoryWithSecurityAlarmType {

  /**
   * A value of 1 indicates an ʼunknownʼ cause. Value should revert to 0 when the alarm conditions
   * are cleared.
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
