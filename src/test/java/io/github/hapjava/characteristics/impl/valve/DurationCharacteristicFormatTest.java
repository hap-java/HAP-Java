package io.github.hapjava.characteristics.impl.valve;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.hapjava.characteristics.impl.windowcovering.CurrentPositionCharacteristic;
import java.util.concurrent.CompletableFuture;
import javax.json.JsonObject;
import org.junit.Test;

/** Regression tests for https://github.com/hap-java/HAP-Java/issues/202 */
public class DurationCharacteristicFormatTest {

  @Test
  public void setDurationAdvertisesUint32() {
    SetDurationCharacteristic characteristic =
        new SetDurationCharacteristic(
            () -> CompletableFuture.completedFuture(300), value -> {}, callback -> {}, () -> {});

    JsonObject json = characteristic.toJson(12).join();

    assertThat(json.getString("type")).isEqualTo("D3");
    assertThat(json.getString("format")).isEqualTo("uint32");
    assertThat(json.getInt("value")).isEqualTo(300);
    assertThat(json.getInt("minValue")).isEqualTo(0);
    assertThat(json.getInt("maxValue")).isEqualTo(3600);
    assertThat(json.getInt("minStep")).isEqualTo(1);
    assertThat(json.getString("unit")).isEqualTo("s");
  }

  @Test
  public void remainingDurationAdvertisesUint32() {
    RemainingDurationCharacteristic characteristic =
        new RemainingDurationCharacteristic(
            () -> CompletableFuture.completedFuture(180), callback -> {}, () -> {});

    JsonObject json = characteristic.toJson(13).join();

    assertThat(json.getString("type")).isEqualTo("D4");
    assertThat(json.getString("format")).isEqualTo("uint32");
    assertThat(json.getInt("value")).isEqualTo(180);
    assertThat(json.getInt("minValue")).isEqualTo(0);
    assertThat(json.getInt("maxValue")).isEqualTo(3600);
    assertThat(json.getString("unit")).isEqualTo("s");
  }

  @Test
  public void otherIntegerCharacteristicsKeepIntFormat() {
    CurrentPositionCharacteristic characteristic =
        new CurrentPositionCharacteristic(
            () -> CompletableFuture.completedFuture(50), callback -> {}, () -> {});

    JsonObject json = characteristic.toJson(14).join();

    assertThat(json.getString("format")).isEqualTo("int");
  }
}
