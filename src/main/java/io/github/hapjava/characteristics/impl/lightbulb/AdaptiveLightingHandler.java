package io.github.hapjava.characteristics.impl.lightbulb;

/** Receives the color temperature and the state of an {@link AdaptiveLightingController}. */
public interface AdaptiveLightingHandler {

  /**
   * Sets the color temperature of the light.
   *
   * @param mired the color temperature in mired, within the range of the color temperature
   *     characteristic
   */
  void applyColorTemperature(int mired);

  /**
   * Stores the state, to be passed to the controller after a restart.
   *
   * @param state the state, or null when Adaptive Lighting is off
   */
  void saveState(byte[] state);
}
