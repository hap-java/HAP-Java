package io.github.hapjava.characteristics.impl;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.hapjava.characteristics.Characteristic;
import io.github.hapjava.characteristics.impl.audio.VolumeCharacteristic;
import io.github.hapjava.characteristics.impl.battery.BatteryLevelCharacteristic;
import io.github.hapjava.characteristics.impl.common.WaterLavelCharacteristic;
import io.github.hapjava.characteristics.impl.filtermaintenance.FilterLifeLevelCharacteristic;
import io.github.hapjava.characteristics.impl.humidifier.HumidityDehumidifierThresholdCharacteristic;
import io.github.hapjava.characteristics.impl.humidifier.HumidityHumidifierThresholdCharacteristic;
import io.github.hapjava.characteristics.impl.humiditysensor.CurrentRelativeHumidityCharacteristic;
import io.github.hapjava.characteristics.impl.humiditysensor.TargetRelativeHumidityCharacteristic;
import io.github.hapjava.characteristics.impl.lightbulb.BrightnessCharacteristic;
import io.github.hapjava.characteristics.impl.lightbulb.HueCharacteristic;
import io.github.hapjava.characteristics.impl.lightbulb.SaturationCharacteristic;
import io.github.hapjava.characteristics.impl.slat.CurrentTiltAngleCharacteristic;
import io.github.hapjava.characteristics.impl.slat.TargetTiltAngleCharacteristic;
import io.github.hapjava.characteristics.impl.windowcovering.CurrentHorizontalTiltAngleCharacteristic;
import io.github.hapjava.characteristics.impl.windowcovering.CurrentPositionCharacteristic;
import io.github.hapjava.characteristics.impl.windowcovering.CurrentVerticalTiltAngleCharacteristic;
import io.github.hapjava.characteristics.impl.windowcovering.TargetHorizontalTiltAngleCharacteristic;
import io.github.hapjava.characteristics.impl.windowcovering.TargetPositionCharacteristic;
import io.github.hapjava.characteristics.impl.windowcovering.TargetVerticalTiltAngleCharacteristic;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import javax.json.JsonObject;
import org.junit.Test;

/** Custom min/max support, see https://github.com/hap-java/HAP-Java/issues/162 */
public class CustomRangeCharacteristicsTest {

  /** Serialized output of the default constructors, captured before custom ranges were added. */
  private static final List<Case> DEFAULT_CONSTRUCTORS =
      Arrays.asList(
          Case.of(
              "BatteryLevelCharacteristic",
              new BatteryLevelCharacteristic(
                  () -> CompletableFuture.completedFuture(50), cb -> {}, () -> {}),
              "{\"iid\":7,\"type\":\"68\",\"perms\":[\"pr\",\"ev\"],\"format\":\"int\",\"value\":50,\"minValue\":0,\"maxValue\":100,\"minStep\":1,\"unit\":\"%\"}"),
          Case.of(
              "BrightnessCharacteristic",
              new BrightnessCharacteristic(
                  () -> CompletableFuture.completedFuture(50), v -> {}, cb -> {}, () -> {}),
              "{\"iid\":7,\"type\":\"8\",\"perms\":[\"pr\",\"pw\",\"ev\"],\"format\":\"int\",\"value\":50,\"minValue\":0,\"maxValue\":100,\"minStep\":1,\"unit\":\"%\"}"),
          Case.of(
              "CurrentPositionCharacteristic",
              new CurrentPositionCharacteristic(
                  () -> CompletableFuture.completedFuture(50), cb -> {}, () -> {}),
              "{\"iid\":7,\"type\":\"6D\",\"perms\":[\"pr\",\"ev\"],\"format\":\"int\",\"value\":50,\"minValue\":0,\"maxValue\":100,\"minStep\":1,\"unit\":\"%\"}"),
          Case.of(
              "TargetPositionCharacteristic",
              new TargetPositionCharacteristic(
                  () -> CompletableFuture.completedFuture(50), v -> {}, cb -> {}, () -> {}),
              "{\"iid\":7,\"type\":\"7C\",\"perms\":[\"pr\",\"pw\",\"ev\"],\"format\":\"int\",\"value\":50,\"minValue\":0,\"maxValue\":100,\"minStep\":1,\"unit\":\"%\"}"),
          Case.of(
              "VolumeCharacteristic",
              new VolumeCharacteristic(
                  () -> CompletableFuture.completedFuture(50), v -> {}, cb -> {}, () -> {}),
              "{\"iid\":7,\"type\":\"119\",\"perms\":[\"pr\",\"pw\",\"ev\"],\"format\":\"int\",\"value\":50,\"minValue\":0,\"maxValue\":100,\"minStep\":1,\"unit\":\"percentage\"}"),
          Case.of(
              "CurrentTiltAngleCharacteristic",
              new CurrentTiltAngleCharacteristic(
                  () -> CompletableFuture.completedFuture(50), cb -> {}, () -> {}),
              "{\"iid\":7,\"type\":\"C1\",\"perms\":[\"pr\",\"ev\"],\"format\":\"int\",\"value\":50,\"minValue\":-90,\"maxValue\":90,\"minStep\":1,\"unit\":\"arcdegrees\"}"),
          Case.of(
              "TargetTiltAngleCharacteristic",
              new TargetTiltAngleCharacteristic(
                  () -> CompletableFuture.completedFuture(50), v -> {}, cb -> {}, () -> {}),
              "{\"iid\":7,\"type\":\"C2\",\"perms\":[\"pr\",\"pw\",\"ev\"],\"format\":\"int\",\"value\":50,\"minValue\":-90,\"maxValue\":90,\"minStep\":1,\"unit\":\"arcdegrees\"}"),
          Case.of(
              "CurrentHorizontalTiltAngleCharacteristic",
              new CurrentHorizontalTiltAngleCharacteristic(
                  () -> CompletableFuture.completedFuture(50), cb -> {}, () -> {}),
              "{\"iid\":7,\"type\":\"6C\",\"perms\":[\"pr\",\"ev\"],\"format\":\"int\",\"value\":50,\"minValue\":-90,\"maxValue\":90,\"minStep\":1,\"unit\":\"arcdegrees\"}"),
          Case.of(
              "TargetHorizontalTiltAngleCharacteristic",
              new TargetHorizontalTiltAngleCharacteristic(
                  () -> CompletableFuture.completedFuture(50), v -> {}, cb -> {}, () -> {}),
              "{\"iid\":7,\"type\":\"7B\",\"perms\":[\"pr\",\"pw\",\"ev\"],\"format\":\"int\",\"value\":50,\"minValue\":-90,\"maxValue\":90,\"minStep\":1,\"unit\":\"arcdegrees\"}"),
          Case.of(
              "CurrentVerticalTiltAngleCharacteristic",
              new CurrentVerticalTiltAngleCharacteristic(
                  () -> CompletableFuture.completedFuture(50), cb -> {}, () -> {}),
              "{\"iid\":7,\"type\":\"6E\",\"perms\":[\"pr\",\"ev\"],\"format\":\"int\",\"value\":50,\"minValue\":-90,\"maxValue\":90,\"minStep\":1,\"unit\":\"arcdegrees\"}"),
          Case.of(
              "TargetVerticalTiltAngleCharacteristic",
              new TargetVerticalTiltAngleCharacteristic(
                  () -> CompletableFuture.completedFuture(50), v -> {}, cb -> {}, () -> {}),
              "{\"iid\":7,\"type\":\"7D\",\"perms\":[\"pr\",\"pw\",\"ev\"],\"format\":\"int\",\"value\":50,\"minValue\":-90,\"maxValue\":90,\"minStep\":1,\"unit\":\"arcdegrees\"}"),
          Case.of(
              "CurrentRelativeHumidityCharacteristic",
              new CurrentRelativeHumidityCharacteristic(
                  () -> CompletableFuture.completedFuture(50.0), cb -> {}, () -> {}),
              "{\"iid\":7,\"type\":\"10\",\"perms\":[\"pr\",\"ev\"],\"format\":\"float\",\"value\":50.0,\"minValue\":0.0,\"maxValue\":100.0,\"minStep\":1.0,\"unit\":\"%\"}"),
          Case.of(
              "TargetRelativeHumidityCharacteristic",
              new TargetRelativeHumidityCharacteristic(
                  () -> CompletableFuture.completedFuture(50.0), v -> {}, cb -> {}, () -> {}),
              "{\"iid\":7,\"type\":\"34\",\"perms\":[\"pr\",\"pw\",\"ev\"],\"format\":\"float\",\"value\":50.0,\"minValue\":0.0,\"maxValue\":100.0,\"minStep\":1.0,\"unit\":\"%\"}"),
          Case.of(
              "FilterLifeLevelCharacteristic",
              new FilterLifeLevelCharacteristic(
                  () -> CompletableFuture.completedFuture(50.0), cb -> {}, () -> {}),
              "{\"iid\":7,\"type\":\"AB\",\"perms\":[\"pr\",\"ev\"],\"format\":\"float\",\"value\":50.0,\"minValue\":0.0,\"maxValue\":100.0,\"minStep\":1.0,\"unit\":\"%\"}"),
          Case.of(
              "HueCharacteristic",
              new HueCharacteristic(
                  () -> CompletableFuture.completedFuture(50.0), v -> {}, cb -> {}, () -> {}),
              "{\"iid\":7,\"type\":\"13\",\"perms\":[\"pr\",\"pw\",\"ev\"],\"format\":\"float\",\"value\":50.0,\"minValue\":0.0,\"maxValue\":360.0,\"minStep\":1.0,\"unit\":\"arcdegrees\"}"),
          Case.of(
              "SaturationCharacteristic",
              new SaturationCharacteristic(
                  () -> CompletableFuture.completedFuture(50.0), v -> {}, cb -> {}, () -> {}),
              "{\"iid\":7,\"type\":\"2F\",\"perms\":[\"pr\",\"pw\",\"ev\"],\"format\":\"float\",\"value\":50.0,\"minValue\":0.0,\"maxValue\":100.0,\"minStep\":1.0,\"unit\":\"%\"}"),
          Case.of(
              "HumidityDehumidifierThresholdCharacteristic",
              new HumidityDehumidifierThresholdCharacteristic(
                  () -> CompletableFuture.completedFuture(50.0), v -> {}, cb -> {}, () -> {}),
              "{\"iid\":7,\"type\":\"C9\",\"perms\":[\"pr\",\"pw\",\"ev\"],\"format\":\"float\",\"value\":50.0,\"minValue\":0.0,\"maxValue\":100.0,\"minStep\":1.0,\"unit\":\"%\"}"),
          Case.of(
              "HumidityHumidifierThresholdCharacteristic",
              new HumidityHumidifierThresholdCharacteristic(
                  () -> CompletableFuture.completedFuture(50.0), v -> {}, cb -> {}, () -> {}),
              "{\"iid\":7,\"type\":\"CA\",\"perms\":[\"pr\",\"pw\",\"ev\"],\"format\":\"float\",\"value\":50.0,\"minValue\":0.0,\"maxValue\":100.0,\"minStep\":1.0,\"unit\":\"%\"}"),
          Case.of(
              "WaterLavelCharacteristic",
              new WaterLavelCharacteristic(
                  () -> CompletableFuture.completedFuture(50.0), cb -> {}, () -> {}),
              "{\"iid\":7,\"type\":\"B5\",\"perms\":[\"pr\",\"ev\"],\"format\":\"float\",\"value\":50.0,\"minValue\":0.0,\"maxValue\":100.0,\"minStep\":1.0,\"unit\":\"%\"}"));

  @Test
  public void defaultConstructorsKeepTheirSerializedOutput() {
    for (Case c : DEFAULT_CONSTRUCTORS) {
      assertThat(c.characteristic.toJson(7).join().toString()).as(c.name).isEqualTo(c.expectedJson);
    }
  }

  @Test
  public void integerCharacteristicsAdvertiseCustomRange() {
    for (Characteristic c :
        Arrays.<Characteristic>asList(
            new BatteryLevelCharacteristic(
                10, 20, () -> CompletableFuture.completedFuture(50), cb -> {}, () -> {}),
            new BrightnessCharacteristic(
                10, 20, () -> CompletableFuture.completedFuture(50), v -> {}, cb -> {}, () -> {}),
            new CurrentPositionCharacteristic(
                10, 20, () -> CompletableFuture.completedFuture(50), cb -> {}, () -> {}),
            new TargetPositionCharacteristic(
                10, 20, () -> CompletableFuture.completedFuture(50), v -> {}, cb -> {}, () -> {}),
            new VolumeCharacteristic(
                10, 20, () -> CompletableFuture.completedFuture(50), v -> {}, cb -> {}, () -> {}),
            new CurrentTiltAngleCharacteristic(
                10, 20, () -> CompletableFuture.completedFuture(50), cb -> {}, () -> {}),
            new TargetTiltAngleCharacteristic(
                10, 20, () -> CompletableFuture.completedFuture(50), v -> {}, cb -> {}, () -> {}),
            new CurrentHorizontalTiltAngleCharacteristic(
                10, 20, () -> CompletableFuture.completedFuture(50), cb -> {}, () -> {}),
            new TargetHorizontalTiltAngleCharacteristic(
                10, 20, () -> CompletableFuture.completedFuture(50), v -> {}, cb -> {}, () -> {}),
            new CurrentVerticalTiltAngleCharacteristic(
                10, 20, () -> CompletableFuture.completedFuture(50), cb -> {}, () -> {}),
            new TargetVerticalTiltAngleCharacteristic(
                10,
                20,
                () -> CompletableFuture.completedFuture(50),
                v -> {},
                cb -> {},
                () -> {}))) {
      JsonObject json = c.toJson(7).join();
      String name = c.getClass().getSimpleName();
      assertThat(json.getInt("minValue")).as(name).isEqualTo(10);
      assertThat(json.getInt("maxValue")).as(name).isEqualTo(20);
      assertThat(json.getInt("minStep")).as(name).isEqualTo(1);
    }
  }

  @Test
  public void floatCharacteristicsAdvertiseCustomRangeAndClampToIt() {
    for (Characteristic c :
        Arrays.<Characteristic>asList(
            new CurrentRelativeHumidityCharacteristic(
                10, 20, 0.5, () -> CompletableFuture.completedFuture(50.0), cb -> {}, () -> {}),
            new TargetRelativeHumidityCharacteristic(
                10,
                20,
                0.5,
                () -> CompletableFuture.completedFuture(50.0),
                v -> {},
                cb -> {},
                () -> {}),
            new FilterLifeLevelCharacteristic(
                10, 20, 0.5, () -> CompletableFuture.completedFuture(50.0), cb -> {}, () -> {}),
            new HueCharacteristic(
                10,
                20,
                0.5,
                () -> CompletableFuture.completedFuture(50.0),
                v -> {},
                cb -> {},
                () -> {}),
            new SaturationCharacteristic(
                10,
                20,
                0.5,
                () -> CompletableFuture.completedFuture(50.0),
                v -> {},
                cb -> {},
                () -> {}),
            new HumidityDehumidifierThresholdCharacteristic(
                10,
                20,
                0.5,
                () -> CompletableFuture.completedFuture(50.0),
                v -> {},
                cb -> {},
                () -> {}),
            new HumidityHumidifierThresholdCharacteristic(
                10,
                20,
                0.5,
                () -> CompletableFuture.completedFuture(50.0),
                v -> {},
                cb -> {},
                () -> {}),
            new WaterLavelCharacteristic(
                10, 20, 0.5, () -> CompletableFuture.completedFuture(50.0), cb -> {}, () -> {}))) {
      JsonObject json = c.toJson(7).join();
      String name = c.getClass().getSimpleName();
      assertThat(json.getJsonNumber("minValue").doubleValue()).as(name).isEqualTo(10.0);
      assertThat(json.getJsonNumber("maxValue").doubleValue()).as(name).isEqualTo(20.0);
      assertThat(json.getJsonNumber("minStep").doubleValue()).as(name).isEqualTo(0.5);
      assertThat(json.getJsonNumber("value").doubleValue()).as(name).isEqualTo(20.0);
    }
  }

  private static class Case {
    final String name;
    final Characteristic characteristic;
    final String expectedJson;

    private Case(String name, Characteristic characteristic, String expectedJson) {
      this.name = name;
      this.characteristic = characteristic;
      this.expectedJson = expectedJson;
    }

    static Case of(String name, Characteristic characteristic, String expectedJson) {
      return new Case(name, characteristic, expectedJson);
    }
  }
}
