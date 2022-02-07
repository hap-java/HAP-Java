package io.github.hapjava.accessories.optionalcharacteristic;

import io.github.hapjava.characteristics.impl.television.PowerModeEnum;
import java.util.concurrent.CompletableFuture;

/** An accessory with power mode. */
public interface AccessoryWithPowerMode {

  /**
   * Set the power mode. See {@link PowerModeEnum} for supported values
   *
   * @param mode target power mode
   * @return a future that completes when the change is made
   */
  CompletableFuture<Void> setPowerMode(PowerModeEnum mode);
}
