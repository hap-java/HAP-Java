package io.github.hapjava.accessories.optionalcharacteristic;

import io.github.hapjava.characteristics.HomekitCharacteristicChangeCallback;
import io.github.hapjava.characteristics.impl.television.PictureModeEnum;
import java.util.concurrent.CompletableFuture;

/**
 * Accessory with picture mode characteristic {@link
 * io.github.hapjava.characteristics.impl.television.PictureModeCharacteristic}.
 */
public interface AccessoryWithPictureMode {

  /**
   * Retrieves the picture mode (see {@link
   * io.github.hapjava.characteristics.impl.television.PictureModeEnum} for supported values).
   *
   * @return a future that will contain the picture mode
   */
  CompletableFuture<PictureModeEnum> getPictureMode();

  /**
   * Set the picture mode (see {@link
   * io.github.hapjava.characteristics.impl.television.PictureModeEnum} for supported values).
   *
   * @param pictureMode picture mode
   * @return a future that completes when the change is made
   */
  CompletableFuture<Void> setPictureMode(PictureModeEnum pictureMode);

  /**
   * Subscribes to changes in the picture mode.
   *
   * @param callback the function to call when the picture mode changes.
   */
  void subscribePictureMode(HomekitCharacteristicChangeCallback callback);

  /** Unsubscribes from changes in the picture mode. */
  void unsubscribePictureMode();
}
