package io.github.hapjava.characteristics.impl.lightbulb;

import io.github.hapjava.characteristics.Characteristic;
import io.github.hapjava.characteristics.HomekitCharacteristicChangeCallback;
import io.github.hapjava.characteristics.impl.base.BaseCharacteristic;
import io.github.hapjava.characteristics.impl.base.IntegerCharacteristic;
import java.io.ByteArrayOutputStream;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Adaptive Lighting for a lightbulb with brightness and color temperature. A HomeKit controller
 * sends a color temperature curve, which is applied in the interval the controller asks for and
 * adjusted to the current brightness.
 *
 * <p>Add {@link #getCharacteristics()} to the lightbulb service. Call {@link #update()} when the
 * brightness changes, and {@link #disable()} when the color temperature, hue or saturation is
 * changed other than through {@link AdaptiveLightingHandler#applyColorTemperature(int)}, including
 * writes from HomeKit.
 */
public class AdaptiveLightingController {

  private static final Logger logger = LoggerFactory.getLogger(AdaptiveLightingController.class);

  // TLV types as named in HAP-NodeJS, which documents the protocol
  private static final int SUPPORTED_TRANSITION_CONFIGURATION = 0x01;
  private static final int SUPPORTED_CHARACTERISTIC_IID = 0x01;
  private static final int SUPPORTED_TRANSITION_TYPE = 0x02;
  private static final byte TRANSITION_TYPE_BRIGHTNESS = 0x01;
  private static final byte TRANSITION_TYPE_COLOR_TEMPERATURE = 0x02;

  private static final int READ_TRANSITION = 0x01;
  private static final int UPDATE_TRANSITION = 0x02;
  private static final int VALUE_TRANSITION_CONFIGURATION = 0x01;

  private static final int VALUE_CONFIGURATION_STATUS = 0x01;
  private static final int STATUS_CHARACTERISTIC_IID = 0x01;
  private static final int STATUS_TRANSITION_PARAMETERS = 0x02;
  private static final int STATUS_TIME_SINCE_START = 0x03;

  private final BrightnessCharacteristic brightness;
  private final ColorTemperatureCharacteristic colorTemperature;
  private final ScheduledExecutorService scheduler;
  private final AdaptiveLightingHandler handler;
  private final List<Characteristic> characteristics;
  private volatile HomekitCharacteristicChangeCallback activeTransitionCountCallback;
  private volatile Transition transition;
  private ScheduledFuture<?> updates;

  /**
   * @param brightness the brightness characteristic of the lightbulb
   * @param colorTemperature the color temperature characteristic of the lightbulb
   * @param scheduler runs the color temperature updates
   * @param handler applies the color temperature and stores the state
   * @param savedState the state last passed to {@link AdaptiveLightingHandler#saveState(byte[])},
   *     or null
   */
  public AdaptiveLightingController(
      BrightnessCharacteristic brightness,
      ColorTemperatureCharacteristic colorTemperature,
      ScheduledExecutorService scheduler,
      AdaptiveLightingHandler handler,
      byte[] savedState) {
    this.brightness = brightness;
    this.colorTemperature = colorTemperature;
    this.scheduler = scheduler;
    this.handler = handler;
    characteristics =
        Arrays.asList(
            new Tlv8Characteristic(
                "00000144-0000-1000-8000-0026BB765291", this::supportedConfiguration, null),
            new Tlv8Characteristic(
                "00000143-0000-1000-8000-0026BB765291", this::transitionStatus, this::handleWrite),
            new IntegerCharacteristic(
                "0000024B-0000-1000-8000-0026BB765291",
                "uint8",
                "",
                0,
                255,
                null,
                Optional.of(() -> CompletableFuture.completedFuture(transition == null ? 0 : 1)),
                Optional.empty(),
                Optional.of(callback -> activeTransitionCountCallback = callback),
                Optional.of(() -> activeTransitionCountCallback = null)) {});
    if (savedState != null) {
      start(Transition.fromState(savedState));
    }
  }

  /** @return the characteristics to add to the lightbulb service */
  public List<Characteristic> getCharacteristics() {
    return characteristics;
  }

  /** Applies the color temperature for the current time and brightness. */
  public synchronized void update() {
    if (transition == null) {
      return;
    }
    Integer mired =
        transition.colorTemperature(System.currentTimeMillis(), brightness.getValue().join());
    if (mired == null) {
      disable();
      return;
    }
    handler.applyColorTemperature(
        Math.max(colorTemperature.getMinValue(), Math.min(colorTemperature.getMaxValue(), mired)));
  }

  /** Turns Adaptive Lighting off. */
  public synchronized void disable() {
    if (transition == null) {
      return;
    }
    transition = null;
    updates.cancel(false);
    handler.saveState(null);
    notifyActiveTransitionCount();
  }

  /** Stops the color temperature updates, e.g. when the accessory is removed. */
  public synchronized void stop() {
    if (updates != null) {
      updates.cancel(false);
    }
  }

  private synchronized void start(Transition transition) {
    this.transition = transition;
    if (updates != null) {
      updates.cancel(false);
    }
    updates =
        scheduler.scheduleWithFixedDelay(
            this::scheduledUpdate, 0, transition.updateInterval, TimeUnit.MILLISECONDS);
    notifyActiveTransitionCount();
  }

  private void scheduledUpdate() {
    // an exception would end the scheduled updates
    try {
      update();
    } catch (RuntimeException e) {
      logger.warn("Could not apply Adaptive Lighting", e);
    }
  }

  private void notifyActiveTransitionCount() {
    HomekitCharacteristicChangeCallback callback = activeTransitionCountCallback;
    if (callback != null) {
      callback.changed();
    }
  }

  private synchronized String handleWrite(String value) {
    Map<Integer, List<byte[]>> request = Tlv8.decode(Base64.getDecoder().decode(value));
    ByteArrayOutputStream response = new ByteArrayOutputStream();
    if (request.containsKey(READ_TRANSITION) && transition != null) {
      Tlv8.write(response, READ_TRANSITION, transition.configuration);
    }
    byte[] update = Tlv8.get(request, UPDATE_TRANSITION);
    if (update != null) {
      byte[] configuration = Tlv8.get(Tlv8.decode(update), VALUE_TRANSITION_CONFIGURATION);
      Transition newTransition = Transition.parse(configuration, System.currentTimeMillis());
      if (newTransition == null) {
        disable();
        Tlv8.write(response, UPDATE_TRANSITION, new byte[0]);
      } else {
        start(newTransition);
        handler.saveState(newTransition.toState());
        Tlv8.write(response, UPDATE_TRANSITION, status(newTransition, 0));
      }
    }
    return Base64.getEncoder().encodeToString(response.toByteArray());
  }

  private String transitionStatus() {
    Transition current = transition;
    if (current == null) {
      return "";
    }
    long timeSinceStart = System.currentTimeMillis() - current.startMillis;
    return Base64.getEncoder().encodeToString(status(current, timeSinceStart));
  }

  private static byte[] status(Transition transition, long timeSinceStart) {
    ByteArrayOutputStream status = new ByteArrayOutputStream();
    Tlv8.write(status, STATUS_CHARACTERISTIC_IID, transition.characteristicIid);
    Tlv8.write(status, STATUS_TRANSITION_PARAMETERS, transition.parameters);
    Tlv8.write(status, STATUS_TIME_SINCE_START, Tlv8.uint(timeSinceStart));
    ByteArrayOutputStream out = new ByteArrayOutputStream();
    Tlv8.write(out, VALUE_CONFIGURATION_STATUS, status.toByteArray());
    return out.toByteArray();
  }

  private String supportedConfiguration() {
    ByteArrayOutputStream out = new ByteArrayOutputStream();
    Tlv8.write(
        out,
        SUPPORTED_TRANSITION_CONFIGURATION,
        supportedTransition(brightness, TRANSITION_TYPE_BRIGHTNESS));
    // list separator
    out.write(0);
    out.write(0);
    Tlv8.write(
        out,
        SUPPORTED_TRANSITION_CONFIGURATION,
        supportedTransition(colorTemperature, TRANSITION_TYPE_COLOR_TEMPERATURE));
    return Base64.getEncoder().encodeToString(out.toByteArray());
  }

  private static byte[] supportedTransition(BaseCharacteristic<?> characteristic, byte type) {
    ByteArrayOutputStream out = new ByteArrayOutputStream();
    Tlv8.write(out, SUPPORTED_CHARACTERISTIC_IID, Tlv8.uint(characteristic.getInstanceId()));
    Tlv8.write(out, SUPPORTED_TRANSITION_TYPE, new byte[] {type});
    return out.toByteArray();
  }
}
