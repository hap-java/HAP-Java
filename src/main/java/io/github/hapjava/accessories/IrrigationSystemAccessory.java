package io.github.hapjava.accessories;

import io.github.hapjava.characteristics.HomekitCharacteristicChangeCallback;
import io.github.hapjava.characteristics.impl.common.ActiveEnum;
import io.github.hapjava.characteristics.impl.common.InUseEnum;
import io.github.hapjava.characteristics.impl.common.ProgramModeEnum;
import io.github.hapjava.services.Service;
import io.github.hapjava.services.impl.IrrigationSystemService;
import java.util.Collection;
import java.util.Collections;
import java.util.concurrent.CompletableFuture;

/** irrigation system accessory that controls several valves. */
public interface IrrigationSystemAccessory extends HomekitAccessory {

  /**
   * An irrigation system is set to Active when the system is enabled. When one of the valves is set
   * to In Use, the irrigation system must be set to in use.
   *
   * @return a future that will contain the binary state
   */
  CompletableFuture<ActiveEnum> getActive();

  /**
   * Sets the irrigation active state
   *
   * @param active the binary state to set
   * @return a future that completes when the change is made
   * @throws Exception when the change cannot be made
   */
  CompletableFuture<Void> setActive(ActiveEnum active) throws Exception;

  /**
   * Retrieves the program mode status
   *
   * @return a future that will contain the valve type.
   */
  CompletableFuture<ProgramModeEnum> getProgramMode();

  /**
   * Retrieves the current inUse state of the irrigation; InUse means one of the valves is set to In
   * Use.
   *
   * @return a future that will contain the binary state
   */
  CompletableFuture<InUseEnum> getInUse();

  /**
   * Subscribes to changes in the inUse state.
   *
   * @param callback the function to call when the state changes.
   */
  void subscribeInUse(HomekitCharacteristicChangeCallback callback);

  /** Unsubscribes from changes in the inUse state. */
  void unsubscribeInUse();

  /**
   * Subscribes to changes in the active state of the irrigation.
   *
   * @param callback the function to call when the state changes.
   */
  void subscribeActive(HomekitCharacteristicChangeCallback callback);

  /** Unsubscribes from changes in the irrigation active state. */
  void unsubscribeActive();

  /**
   * Subscribes to changes in the program mode of the irrigation system.
   *
   * @param callback the function to call when the state changes.
   */
  void subscribeProgramMode(HomekitCharacteristicChangeCallback callback);

  /** Unsubscribes from changes in the program mode. */
  void unsubscribeProgramMode();

  @Override
  default Collection<Service> getServices() {
    return Collections.singleton(new IrrigationSystemService(this));
  }
}
