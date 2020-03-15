package io.github.hapjava.accessories.optionalcharacteristic;

import io.github.hapjava.characteristics.HomekitCharacteristicChangeCallback;
import java.util.concurrent.CompletableFuture;

/**
 * Accessory with Color
 *
 * @author Andy Lintner
 */
public interface AccessoryWithColor {

  /**
   * Retrieves the current hue of the light.
   *
   * @return a future that will contain the hue, expressed in arc degrees from 0 to 360.
   */
  CompletableFuture<Double> getHue();

  /**
   * Sets the current hue of the light
   *
   * @param value the hue to set, expressed in arc degrees from 0 to 360.
   * @return a future that completes when the hue is changed
   * @throws Exception when the hue cannot be changed.
   */
  CompletableFuture<Void> setHue(Double value) throws Exception;

  /**
   * Subscribes to changes in the hue of the light.
   *
   * @param callback the function to call when the state changes.
   */
  void subscribeHue(HomekitCharacteristicChangeCallback callback);

  /** Unsubscribes from changes in the hue of the light. */
  void unsubscribeHue();

  /**
   * Retrieves the saturation of the light.
   *
   * @return a future that will contain the saturation, expressed as a value between 0 and 100.
   */
  CompletableFuture<Double> getSaturation();

  /**
   * Sets the saturation of the light.
   *
   * @param value the saturation to set, expressed as a value between 0 and 100.
   * @return a future that completes when the saturation is changed.
   * @throws Exception when the saturation cannot be set.
   */
  CompletableFuture<Void> setSaturation(Double value) throws Exception;

  /**
   * Subscribes to changes in the saturation of the light.
   *
   * @param callback the function to call when the state changes.
   */
  void subscribeSaturation(HomekitCharacteristicChangeCallback callback);

  /** Unsubscribes from changes in the saturation of the light. */
  void unsubscribeSaturation();
}
