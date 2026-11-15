package io.github.hapjava.characteristics.impl.lightbulb;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.ByteArrayOutputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import org.junit.Test;

public class TransitionTest {

  static final byte[] PARAMETERS = {1, 2, 3, 4};

  /** Curve: 300 mired held for 500 ms, then 1000 ms to 400 mired; factor -1 per % brightness. */
  static byte[] configuration() {
    ByteArrayOutputStream curve = new ByteArrayOutputStream();
    Tlv8.write(curve, 0x01, entry(300, 0, null));
    curve.write(0);
    curve.write(0);
    Tlv8.write(curve, 0x01, entry(400, 1000, 500L));
    ByteArrayOutputStream range = new ByteArrayOutputStream();
    Tlv8.write(range, 0x01, new byte[] {10, 0, 0, 0});
    Tlv8.write(range, 0x02, new byte[] {100, 0, 0, 0});
    Tlv8.write(curve, 0x03, range.toByteArray());

    ByteArrayOutputStream configuration = new ByteArrayOutputStream();
    Tlv8.write(configuration, 0x01, new byte[] {10});
    Tlv8.write(configuration, 0x02, PARAMETERS);
    Tlv8.write(configuration, 0x03, new byte[] {1});
    Tlv8.write(configuration, 0x05, curve.toByteArray());
    Tlv8.write(configuration, 0x06, Tlv8.uint(60_000));
    return configuration.toByteArray();
  }

  private static byte[] entry(float value, long transitionTime, Long hold) {
    ByteArrayOutputStream entry = new ByteArrayOutputStream();
    Tlv8.write(entry, 0x01, float32(-1));
    Tlv8.write(entry, 0x02, float32(value));
    Tlv8.write(entry, 0x03, Tlv8.uint(transitionTime));
    if (hold != null) {
      Tlv8.write(entry, 0x04, Tlv8.uint(hold));
    }
    return entry.toByteArray();
  }

  private static byte[] float32(float value) {
    return ByteBuffer.allocate(4).order(ByteOrder.LITTLE_ENDIAN).putFloat(value).array();
  }

  @Test
  public void followsTheCurveAdjustedByBrightness() {
    Transition transition = Transition.parse(configuration(), 1000);

    assertThat(transition.colorTemperature(1000, 50)).isEqualTo(250);
    assertThat(transition.colorTemperature(1500, 50)).isEqualTo(250);
    assertThat(transition.colorTemperature(2000, 50)).isEqualTo(300);
    assertThat(transition.colorTemperature(2500, 50)).isEqualTo(350);
    assertThat(transition.colorTemperature(2501, 50)).isNull();
  }

  @Test
  public void brightnessIsLimitedToTheRange() {
    Transition transition = Transition.parse(configuration(), 0);

    assertThat(transition.colorTemperature(0, 1)).isEqualTo(290);
    assertThat(transition.colorTemperature(0, 100)).isEqualTo(200);
  }

  @Test
  public void configurationWithoutEnabledFlagDisables() {
    assertThat(Transition.parse(new byte[] {0x01, 0x01, 10}, 0)).isNull();
  }

  @Test
  public void stateKeepsStartTimeAndConfiguration() {
    Transition transition = Transition.fromState(Transition.parse(configuration(), 1000).toState());

    assertThat(transition.startMillis).isEqualTo(1000);
    assertThat(transition.configuration).isEqualTo(configuration());
    assertThat(transition.parameters).isEqualTo(PARAMETERS);
    assertThat(transition.updateInterval).isEqualTo(60_000);
  }
}
