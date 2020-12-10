package io.github.hapjava.accessories;

import io.github.hapjava.characteristics.HomekitCharacteristicChangeCallback;
import io.github.hapjava.characteristics.impl.filtermaintenance.FilterChangeIndicationEnum;
import io.github.hapjava.services.Service;
import io.github.hapjava.services.impl.FilterMaintenanceService;
import java.util.Collection;
import java.util.Collections;
import java.util.concurrent.CompletableFuture;

/**
 * A Filter maintenance with mandatory characteristics.
 *
 * <p>The HomeKit app doesn't support a separate FilterMaintenance, but as a linked service to
 * AirPurifier.
 *
 */
public interface FilterMaintenanceAccessory extends HomekitAccessory {

  /**
   * The filter change indictaion. It's either yes or no.
   *
   * @return FilterChangeIndicationEnum
   */
  CompletableFuture<FilterChangeIndicationEnum> getFilterChangeIndication();

  /**
   * Subscribes to changes in the filter change indication.
   *
   * @param callback the function to call when the state changes.
   */
  void subscribeFilterChangeIndication(HomekitCharacteristicChangeCallback callback);

  /** Unsubscribes from changes in the filter change indication. */
  void unsubscribeFilterChangeIndication();

  @Override
  default Collection<Service> getServices() {
    return Collections.singleton(new FilterMaintenanceService(this));
  }
}
