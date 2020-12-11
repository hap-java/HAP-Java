package io.github.hapjava.accessories.optionalcharacteristic;

import io.github.hapjava.characteristics.HomekitCharacteristicChangeCallback;
import java.util.concurrent.CompletableFuture;

/** Accessory with filter level characteristics */
public interface AccessoryWithFilterLifeLevel {

  /**
   * what's the filter life level, percentage wise
   *
   * @return filter life level
   */
  CompletableFuture<Double> getFilterLifeLevel();

  /**
   * Subscribes to changes in the filter life level.
   *
   * @param callback the function to call when the level changes.
   */
  void subscribeFilterLifeLevel(HomekitCharacteristicChangeCallback callback);

  /** Unsubscribes from changes in the current filter life level. */
  void unsubscribeFilterLifeLevel();
}
