package io.github.hapjava.accessories.optionalcharacteristic;

import io.github.hapjava.characteristics.HomekitCharacteristicChangeCallback;
import java.util.concurrent.CompletableFuture;

/** Accessory with nitrogen dioxide density characteristic. */
public interface AccessoryWithNitrogenDioxideDensity {

  /**
   * Retrieves the nitrogen dioxide density.
   *
   * @return a future with the nitrogen dioxide density
   */
  CompletableFuture<Double> getNitrogenDioxideDensity();

  /**
   * Subscribes to changes in nitrogen dioxide density.
   *
   * @param callback the function when nitrogen dioxide density changes
   */
  void subscribeNitrogenDioxideDensity(HomekitCharacteristicChangeCallback callback);

  /** Unsubscribes from changes */
  void unsubscribeNitrogenDioxideDensity();
}
