package io.github.hapjava.characteristics.impl.lightbulb;

import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/** Color temperature curve sent by a HomeKit controller when it enables Adaptive Lighting. */
final class Transition {

  // TLV types as named in HAP-NodeJS, which documents the protocol
  private static final int CHARACTERISTIC_IID = 0x01;
  private static final int TRANSITION_PARAMETERS = 0x02;
  private static final int ENABLED = 0x03; // HAP-NodeJS: UNKNOWN_3, absent when disabling
  private static final int CURVE = 0x05;
  private static final int UPDATE_INTERVAL = 0x06;

  private static final int CURVE_ENTRY = 0x01;
  private static final int BRIGHTNESS_RANGE = 0x03;

  private static final int ADJUSTMENT_FACTOR = 0x01;
  private static final int VALUE = 0x02;
  private static final int TRANSITION_TIME = 0x03;
  private static final int HOLD = 0x04; // HAP-NodeJS: DURATION

  private static final int MIN_BRIGHTNESS = 0x01;
  private static final int MAX_BRIGHTNESS = 0x02;

  final byte[] configuration;
  final long startMillis;
  final byte[] characteristicIid;
  final byte[] parameters;
  final long updateInterval;

  private final float[] values;
  private final float[] adjustmentFactors;
  private final long[] transitionTimes;
  // time an entry is held before the transition to the next one starts
  private final long[] holds;
  private final int minBrightness;
  private final int maxBrightness;

  private Transition(Map<Integer, List<byte[]>> tlv, byte[] configuration, long startMillis) {
    this.configuration = configuration;
    this.startMillis = startMillis;
    characteristicIid = Tlv8.get(tlv, CHARACTERISTIC_IID);
    parameters = Tlv8.get(tlv, TRANSITION_PARAMETERS);
    byte[] interval = Tlv8.get(tlv, UPDATE_INTERVAL);
    updateInterval = interval == null ? 60_000 : Tlv8.readUint(interval);

    Map<Integer, List<byte[]>> curve = Tlv8.decode(Tlv8.get(tlv, CURVE));
    List<byte[]> entries = curve.get(CURVE_ENTRY);
    values = new float[entries.size()];
    adjustmentFactors = new float[entries.size()];
    transitionTimes = new long[entries.size()];
    holds = new long[entries.size()];
    for (int i = 0; i < entries.size(); i++) {
      Map<Integer, List<byte[]>> entry = Tlv8.decode(entries.get(i));
      values[i] = Tlv8.readFloat(Tlv8.get(entry, VALUE));
      adjustmentFactors[i] = Tlv8.readFloat(Tlv8.get(entry, ADJUSTMENT_FACTOR));
      transitionTimes[i] = Tlv8.readUint(Tlv8.get(entry, TRANSITION_TIME));
      byte[] hold = Tlv8.get(entry, HOLD);
      if (i > 0 && hold != null) {
        holds[i - 1] = Tlv8.readUint(hold);
      }
    }
    Map<Integer, List<byte[]>> range = Tlv8.decode(Tlv8.get(curve, BRIGHTNESS_RANGE));
    minBrightness = (int) Tlv8.readUint(Tlv8.get(range, MIN_BRIGHTNESS));
    maxBrightness = (int) Tlv8.readUint(Tlv8.get(range, MAX_BRIGHTNESS));
  }

  /** @return the transition, or null if the configuration disables Adaptive Lighting. */
  static Transition parse(byte[] configuration, long startMillis) {
    Map<Integer, List<byte[]>> tlv = Tlv8.decode(configuration);
    return tlv.containsKey(ENABLED) ? new Transition(tlv, configuration, startMillis) : null;
  }

  static Transition fromState(byte[] state) {
    return parse(Arrays.copyOfRange(state, 8, state.length), ByteBuffer.wrap(state).getLong());
  }

  byte[] toState() {
    return ByteBuffer.allocate(8 + configuration.length)
        .putLong(startMillis)
        .put(configuration)
        .array();
  }

  /** @return the color temperature in mired, or null if the curve has ended. */
  Integer colorTemperature(long timeMillis, int brightness) {
    long offset = timeMillis - startMillis;
    long reached = 0;
    for (int i = 0; i + 1 < values.length; i++) {
      reached += transitionTimes[i];
      if (offset >= reached && offset <= reached + holds[i] + transitionTimes[i + 1]) {
        long transitioning = offset - reached - holds[i];
        float progress = transitioning <= 0 ? 0 : (float) transitioning / transitionTimes[i + 1];
        float value = values[i] + (values[i + 1] - values[i]) * progress;
        float factor =
            adjustmentFactors[i] + (adjustmentFactors[i + 1] - adjustmentFactors[i]) * progress;
        int adjustedBrightness = Math.max(minBrightness, Math.min(maxBrightness, brightness));
        return Math.round(value + factor * adjustedBrightness);
      }
      reached += holds[i];
    }
    return null;
  }
}
