package io.github.hapjava.characteristics.impl;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.hapjava.characteristics.Characteristic;
import io.github.hapjava.characteristics.impl.slat.CurrentTiltAngleCharacteristic;
import io.github.hapjava.characteristics.impl.slat.TargetTiltAngleCharacteristic;
import io.github.hapjava.characteristics.impl.windowcovering.CurrentHorizontalTiltAngleCharacteristic;
import io.github.hapjava.characteristics.impl.windowcovering.CurrentVerticalTiltAngleCharacteristic;
import io.github.hapjava.characteristics.impl.windowcovering.TargetHorizontalTiltAngleCharacteristic;
import io.github.hapjava.characteristics.impl.windowcovering.TargetVerticalTiltAngleCharacteristic;
import java.util.Arrays;
import java.util.concurrent.CompletableFuture;
import javax.json.JsonObject;
import org.junit.Test;

/** Custom tilt angle ranges, see https://github.com/hap-java/HAP-Java/issues/162 */
public class CustomTiltAngleRangeTest {

  @Test
  public void defaultConstructorsKeepTheirSerializedOutput() {
    // Serialized output captured before custom ranges were added.
    for (Case c :
        Arrays.asList(
            Case.of(
                new CurrentTiltAngleCharacteristic(
                    () -> CompletableFuture.completedFuture(50), cb -> {}, () -> {}),
                "{\"iid\":7,\"type\":\"C1\",\"perms\":[\"pr\",\"ev\"],\"format\":\"int\",\"value\":50,\"minValue\":-90,\"maxValue\":90,\"minStep\":1,\"unit\":\"arcdegrees\"}"),
            Case.of(
                new TargetTiltAngleCharacteristic(
                    () -> CompletableFuture.completedFuture(50), v -> {}, cb -> {}, () -> {}),
                "{\"iid\":7,\"type\":\"C2\",\"perms\":[\"pr\",\"pw\",\"ev\"],\"format\":\"int\",\"value\":50,\"minValue\":-90,\"maxValue\":90,\"minStep\":1,\"unit\":\"arcdegrees\"}"),
            Case.of(
                new CurrentHorizontalTiltAngleCharacteristic(
                    () -> CompletableFuture.completedFuture(50), cb -> {}, () -> {}),
                "{\"iid\":7,\"type\":\"6C\",\"perms\":[\"pr\",\"ev\"],\"format\":\"int\",\"value\":50,\"minValue\":-90,\"maxValue\":90,\"minStep\":1,\"unit\":\"arcdegrees\"}"),
            Case.of(
                new TargetHorizontalTiltAngleCharacteristic(
                    () -> CompletableFuture.completedFuture(50), v -> {}, cb -> {}, () -> {}),
                "{\"iid\":7,\"type\":\"7B\",\"perms\":[\"pr\",\"pw\",\"ev\"],\"format\":\"int\",\"value\":50,\"minValue\":-90,\"maxValue\":90,\"minStep\":1,\"unit\":\"arcdegrees\"}"),
            Case.of(
                new CurrentVerticalTiltAngleCharacteristic(
                    () -> CompletableFuture.completedFuture(50), cb -> {}, () -> {}),
                "{\"iid\":7,\"type\":\"6E\",\"perms\":[\"pr\",\"ev\"],\"format\":\"int\",\"value\":50,\"minValue\":-90,\"maxValue\":90,\"minStep\":1,\"unit\":\"arcdegrees\"}"),
            Case.of(
                new TargetVerticalTiltAngleCharacteristic(
                    () -> CompletableFuture.completedFuture(50), v -> {}, cb -> {}, () -> {}),
                "{\"iid\":7,\"type\":\"7D\",\"perms\":[\"pr\",\"pw\",\"ev\"],\"format\":\"int\",\"value\":50,\"minValue\":-90,\"maxValue\":90,\"minStep\":1,\"unit\":\"arcdegrees\"}"))) {
      assertThat(c.characteristic.toJson(7).join().toString())
          .as(c.characteristic.getClass().getSimpleName())
          .isEqualTo(c.expected);
    }
  }

  @Test
  public void customRangeIsAdvertised() {
    for (Characteristic c :
        Arrays.<Characteristic>asList(
            new CurrentTiltAngleCharacteristic(
                0, 90, () -> CompletableFuture.completedFuture(50), cb -> {}, () -> {}),
            new TargetTiltAngleCharacteristic(
                0, 90, () -> CompletableFuture.completedFuture(50), v -> {}, cb -> {}, () -> {}),
            new CurrentHorizontalTiltAngleCharacteristic(
                0, 90, () -> CompletableFuture.completedFuture(50), cb -> {}, () -> {}),
            new TargetHorizontalTiltAngleCharacteristic(
                0, 90, () -> CompletableFuture.completedFuture(50), v -> {}, cb -> {}, () -> {}),
            new CurrentVerticalTiltAngleCharacteristic(
                0, 90, () -> CompletableFuture.completedFuture(50), cb -> {}, () -> {}),
            new TargetVerticalTiltAngleCharacteristic(
                0, 90, () -> CompletableFuture.completedFuture(50), v -> {}, cb -> {}, () -> {}))) {
      JsonObject json = c.toJson(7).join();
      String name = c.getClass().getSimpleName();
      assertThat(json.getInt("minValue")).as(name).isEqualTo(0);
      assertThat(json.getInt("maxValue")).as(name).isEqualTo(90);
      assertThat(json.getInt("minStep")).as(name).isEqualTo(1);
    }
  }

  private static class Case {
    final Characteristic characteristic;
    final String expected;

    private Case(Characteristic characteristic, String expected) {
      this.characteristic = characteristic;
      this.expected = expected;
    }

    static Case of(Characteristic characteristic, String expected) {
      return new Case(characteristic, expected);
    }
  }
}
