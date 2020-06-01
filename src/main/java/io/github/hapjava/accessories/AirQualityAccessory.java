package io.github.hapjava.accessories;

import io.github.hapjava.characteristics.HomekitCharacteristicChangeCallback;
import io.github.hapjava.characteristics.impl.airquality.AirQualityEnum;
import io.github.hapjava.services.Service;
import io.github.hapjava.services.impl.AirQualityService;
import java.util.Collection;
import java.util.Collections;
import java.util.concurrent.CompletableFuture;

/** An air quality accessory which can include several sensors. */
public interface AirQualityAccessory extends HomekitAccessory {

  /**
   * Retrieves the state of the air quality
   *
   * @return a future that will contain the state
   */
  CompletableFuture<AirQualityEnum> getAirQuality();

  /**
   * Subscribes to changes in the air quality
   *
   * @param callback the function to call when the air quality changes.
   */
  void subscribeAirQuality(HomekitCharacteristicChangeCallback callback);

  /** Unsubscribes from changes in the air quality. */
  void unsubscribeAirQuality();

  @Override
  default Collection<Service> getServices() {
    return Collections.singleton(new AirQualityService(this));
  }
}
