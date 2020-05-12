package io.github.hapjava.accessories.optionalcharacteristic;

import io.github.hapjava.characteristics.HomekitCharacteristicChangeCallback;
import java.util.concurrent.CompletableFuture;

/** Accessory with sulphur dioxide density characteristic. */
public interface AccessoryWithSulphurDioxideDensity {

  /**
   * Retrieves the sulphur dioxide density.
   *
   * @return a future with the sulphur dioxide density
   */
  CompletableFuture<Double> getSulphurDioxideDensity();

  /**
   * Subscribes to changes in sulphur dioxide density.
   *
   * @param callback the function when sulphur dioxide density changes
   */
  void subscribeSulphurDioxideDensity(HomekitCharacteristicChangeCallback callback);

  /** Unsubscribes from changes */
  void unsubscribeSulphurDioxideDensity();
}
