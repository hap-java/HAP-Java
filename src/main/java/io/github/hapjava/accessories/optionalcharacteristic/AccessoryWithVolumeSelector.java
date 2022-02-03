package io.github.hapjava.accessories.optionalcharacteristic;

import io.github.hapjava.characteristics.impl.televisionspeaker.VolumeSelectorEnum;
import java.util.concurrent.CompletableFuture;

/**
 * Accessory with volume selector {@link
 * io.github.hapjava.characteristics.impl.televisionspeaker.VolumeSelectorCharacteristic}
 */
public interface AccessoryWithVolumeSelector {

  /**
   * Sets the volume selector
   *
   * @param value the volume selector
   * @return a future that completes when the volume selector is changed
   * @throws Exception when the volume selector cannot be set
   */
  CompletableFuture<Void> setVolumeSelector(VolumeSelectorEnum value) throws Exception;
}
