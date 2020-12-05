package io.github.hapjava.accessories;

import io.github.hapjava.characteristics.HomekitCharacteristicChangeCallback;
import io.github.hapjava.characteristics.impl.securitysystem.CurrentSecuritySystemStateEnum;
import io.github.hapjava.characteristics.impl.securitysystem.TargetSecuritySystemStateEnum;
import io.github.hapjava.services.Service;
import io.github.hapjava.services.impl.SecuritySystemService;
import java.util.Collection;
import java.util.Collections;
import java.util.concurrent.CompletableFuture;

/**
 * A security system that can be armed so that when a contact sensor is opened or a motion sensor
 * detects movement, then a siren could be fired off. There are different modes for arming the
 * system. See {@link TargetSecuritySystemStateEnum} for more information.
 *
 * @author Gaston Dombiak
 */
public interface SecuritySystemAccessory extends HomekitAccessory {

  /**
   * Retrieves the current state of the security system. The state describes if the system is armed
   * in any of its variations; or if the alarm has been triggered; or if the system is disarmed.
   *
   * @return current state of the security system.
   */
  CompletableFuture<CurrentSecuritySystemStateEnum> getCurrentSecuritySystemState();

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
  void setTargetSecuritySystemState(TargetSecuritySystemStateEnum state) throws Exception;

  /**
   * Retrieves the pending, but not yet completed, state of the security system.
   *
   * @return target state of the security system.
   */
  CompletableFuture<TargetSecuritySystemStateEnum> getTargetSecuritySystemState();

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
   * Valid values for current state.
   *
   * @return array of valid current states.
   */
  default CurrentSecuritySystemStateEnum[] getCurrentSecuritySystemStateValidValues() {
    return CurrentSecuritySystemStateEnum.values();
  }

  /**
   * Valid values for target state.
   *
   * @return array of valid targe states.
   */
  default TargetSecuritySystemStateEnum[] getTargetSecuritySystemStateValidValues() {
    return TargetSecuritySystemStateEnum.values();
  }

  @Override
  default Collection<Service> getServices() {
    return Collections.singleton(new SecuritySystemService(this));
  }
}
