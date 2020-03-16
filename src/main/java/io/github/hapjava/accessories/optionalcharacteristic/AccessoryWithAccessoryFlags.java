package io.github.hapjava.accessories.optionalcharacteristic;

import io.github.hapjava.characteristics.HomekitCharacteristicChangeCallback;
import io.github.hapjava.characteristics.impl.accessoryinformation.AccessoryFlagsEnum;
import java.util.concurrent.CompletableFuture;

/**
 * Accessory with accessory flags, which indicates that additional setup is required
 *
 * @author Eugen Freiter
 */
public interface AccessoryWithAccessoryFlags {

  /**
   * When set indicates accessory requires additional setup.
   *
   * @return a future that will contain the accessory flags
   */
  CompletableFuture<AccessoryFlagsEnum> getAccessoryFlags();

  /**
   * Subscribes to changes in accessory flags
   *
   * @param callback the function to call when the accessory flags changes.
   */
  void subscribeAccessoryFlags(HomekitCharacteristicChangeCallback callback);

  /** Unsubscribes from changes in the accessory flags . */
  void unsubscribeAccessoryFlags();
}
