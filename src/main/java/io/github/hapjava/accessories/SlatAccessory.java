package io.github.hapjava.accessories;

import io.github.hapjava.characteristics.HomekitCharacteristicChangeCallback;
import io.github.hapjava.characteristics.impl.slat.CurrentSlatStateEnum;
import io.github.hapjava.characteristics.impl.slat.SlatTypeEnum;
import io.github.hapjava.services.Service;
import io.github.hapjava.services.impl.SlatService;
import java.util.Collection;
import java.util.Collections;
import java.util.concurrent.CompletableFuture;

/**
 * A slat which tilts on a vertical or a horizontal axis.
 *
 * @author Eugen Freiter
 */
public interface SlatAccessory extends HomekitAccessory {

  /**
   * Retrieves the current state of the slat
   *
   * @return a future that will contain the state
   */
  CompletableFuture<CurrentSlatStateEnum> getSlatState();

  /**
   * Subscribes to changes in the state of the slat.
   *
   * @param callback the function to call when the state changes.
   */
  void subscribeSlatState(HomekitCharacteristicChangeCallback callback);

  /** Unsubscribes from changes in the state of the slat. */
  void unsubscribeSlatState();

  /**
   * Retrieves the slat type.
   *
   * @return a future that will slat type.
   */
  CompletableFuture<SlatTypeEnum> getSlatType();

  @Override
  default Collection<Service> getServices() {
    return Collections.singleton(new SlatService(this));
  }
}
