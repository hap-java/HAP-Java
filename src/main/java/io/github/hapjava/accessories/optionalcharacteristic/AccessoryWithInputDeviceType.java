package io.github.hapjava.accessories.optionalcharacteristic;

import io.github.hapjava.characteristics.HomekitCharacteristicChangeCallback;
import io.github.hapjava.characteristics.impl.inputsource.InputDeviceTypeEnum;
import java.util.concurrent.CompletableFuture;

/**
 * Accessory with input devices, e.g. television. {@link
 * io.github.hapjava.characteristics.impl.inputsource.InputDeviceTypeCharacteristic}
 */
public interface AccessoryWithInputDeviceType {

  /**
   * Retrieves the input device type. See {@link InputDeviceTypeEnum} for supported values.
   *
   * @return a future with the value
   */
  CompletableFuture<InputDeviceTypeEnum> getInputDeviceType();

  /**
   * Subscribes to changes in input device type.
   *
   * @param callback the function when the input device type changes
   */
  void subscribeInputDeviceType(HomekitCharacteristicChangeCallback callback);

  /** Unsubscribes from changes */
  void unsubscribeInputDeviceType();
}
