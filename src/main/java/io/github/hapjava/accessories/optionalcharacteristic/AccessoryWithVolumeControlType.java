package io.github.hapjava.accessories.optionalcharacteristic;

import io.github.hapjava.characteristics.HomekitCharacteristicChangeCallback;
import io.github.hapjava.characteristics.impl.televisionspeaker.VolumeControlTypeEnum;
import java.util.concurrent.CompletableFuture;

/**
 * Accessory with volume control type {@link
 * io.github.hapjava.characteristics.impl.televisionspeaker.VolumeControlTypeCharacteristic}
 */
public interface AccessoryWithVolumeControlType {

  /**
   * Retrieves the current volume control type. see {@link VolumeControlTypeEnum} for possible
   * values
   *
   * @return a future that will contain the type.
   */
  CompletableFuture<VolumeControlTypeEnum> getVolumeControlType();

  /**
   * Subscribes to changes in the volume.
   *
   * @param callback the function to call when the state changes.
   */
  void subscribeVolumeControlType(HomekitCharacteristicChangeCallback callback);

  /** Unsubscribes from changes in the volume. */
  void unsubscribeVolumeControlType();
}
