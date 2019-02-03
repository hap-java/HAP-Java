package com.beowulfe.hap.accessories;

import com.beowulfe.hap.HomekitAccessory;
import com.beowulfe.hap.HomekitCharacteristicChangeCallback;
import com.beowulfe.hap.Service;
import com.beowulfe.hap.accessories.properties.CurrentSecuritySystemState;
import com.beowulfe.hap.accessories.properties.SecuritySystemAlarmType;
import com.beowulfe.hap.accessories.properties.TargetSecuritySystemState;
import com.beowulfe.hap.impl.services.SecuritySystemService;
import java.util.Collection;
import java.util.Collections;
import java.util.concurrent.CompletableFuture;

/**
 * A security system that can be armed so that when a contact sensor is opened or a motion sensor
 * detects movement, then a siren could be fired off. There are different modes for arming the
 * system. See {@link TargetSecuritySystemState} for more information.
 *
 * @author Gaston Dombiak
 */
public interface SecuritySystem extends HomekitAccessory {

  /**
   * Retrieves the current state of the security system. The state describes if the system is armed
   * in any of its variations; or if the alarm has been triggered; or if the system is disarmed.
   *
   * @return current state of the security system.
   */
  CompletableFuture<CurrentSecuritySystemState> getCurrentSecuritySystemState();

  /**
   * Subscribes to changes to the state of the security system.
   *
   * @param callback the function to call when the state changes.
   */
  void subscribeCurrentSecuritySystemState(HomekitCharacteristicChangeCallback callback);

  /** Unsubscribes from changes in the state of the security system. */
  void unsubscribeCurrentSecuritySystemState();

  /**
   * Sets the state of the security system. The security system could be armed in any of its
   * variations or disarmed.
   *
   * @param state target state of the security system.
   * @throws Exception when the change cannot be made.
   */
  void setTargetSecuritySystemState(TargetSecuritySystemState state) throws Exception;

  /**
   * Retrieves the pending, but not yet completed, state of the security system.
   *
   * @return target state of the security system.
   */
  CompletableFuture<TargetSecuritySystemState> getTargetSecuritySystemState();

  /**
   * Subscribes to changes in the pending, but not yet completed, state of the security system.
   *
   * @param callback the function to call when the state changes.
   */
  void subscribeTargetSecuritySystemState(HomekitCharacteristicChangeCallback callback);

  /**
   * Unsubscribes from changes in the pending, but not yet completed, state of the security system.
   */
  void unsubscribeTargetSecuritySystemState();

  /**
   * Retrieves the alarm type of the security system.
   *
   * @return alarm type of the security system.
   */
  CompletableFuture<SecuritySystemAlarmType> getAlarmTypeState();

  /**
   * Subscribes to changes to the alarm type of the security system.
   *
   * @param callback the function to call when the alarm type changes.
   */
  void subscribeAlarmTypeState(HomekitCharacteristicChangeCallback callback);

  /** Unsubscribes from changes in the alarm type of the security system. */
  void unsubscribeAlarmTypeState();

  @Override
  default Collection<Service> getServices() {
    return Collections.singleton(new SecuritySystemService(this));
  }
}
