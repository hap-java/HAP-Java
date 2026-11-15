package io.github.hapjava.characteristics.impl.lightbulb;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.ArgumentMatchers.notNull;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import io.github.hapjava.characteristics.HomekitCharacteristicChangeCallback;
import io.github.hapjava.characteristics.WriteResponseCharacteristic;
import io.github.hapjava.characteristics.impl.base.BaseCharacteristic;
import java.io.ByteArrayOutputStream;
import java.util.Base64;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonString;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

public class AdaptiveLightingControllerTest {

  private final BrightnessCharacteristic brightness =
      new BrightnessCharacteristic(
          () -> CompletableFuture.completedFuture(50), v -> {}, c -> {}, () -> {});
  private final ColorTemperatureCharacteristic colorTemperature =
      new ColorTemperatureCharacteristic(
          140, 500, () -> CompletableFuture.completedFuture(300), v -> {}, c -> {}, () -> {});
  private final ScheduledExecutorService scheduler = mock(ScheduledExecutorService.class);
  private final ScheduledFuture<?> updates = mock(ScheduledFuture.class);
  private final AdaptiveLightingHandler handler = mock(AdaptiveLightingHandler.class);

  @Before
  public void setUp() {
    doReturn(updates)
        .when(scheduler)
        .scheduleWithFixedDelay(any(), anyLong(), anyLong(), any(TimeUnit.class));
  }

  @Test
  public void supportedConfigurationNamesBrightnessAndColorTemperature() {
    brightness.setInstanceId(9);
    colorTemperature.setInstanceId(10);
    AdaptiveLightingController controller = controller(null);

    assertThat(value(controller, 0))
        .containsExactly(1, 6, 1, 1, 9, 2, 1, 1, 0, 0, 1, 6, 1, 1, 10, 2, 1, 2);
  }

  @Test
  public void controlPointAnswersWritesWithAResponse() {
    JsonObject json = characteristic(controller(null), 1).toJson(1).join();

    assertThat(json.getString("format")).isEqualTo("tlv8");
    assertThat(json.getJsonArray("perms").getValuesAs(JsonString.class))
        .extracting(JsonString::getString)
        .containsExactly("pr", "pw", "wr");
  }

  @Test
  public void enablingStartsTheUpdatesAndAnswersWithTheStatus() throws Exception {
    AdaptiveLightingController controller = controller(null);
    HomekitCharacteristicChangeCallback callback = mock(HomekitCharacteristicChangeCallback.class);
    characteristic(controller, 2).subscribe(callback);

    byte[] response = write(controller, tlv(0x02, tlv(0x01, TransitionTest.configuration())));

    assertThat(response).containsExactly(2, 14, 1, 12, 1, 1, 10, 2, 4, 1, 2, 3, 4, 3, 1, 0);
    verify(handler).saveState(notNull());
    verify(callback).changed();
    assertThat(characteristic(controller, 2).getValue().join()).isEqualTo(1);
    runUpdate();
    verify(handler).applyColorTemperature(250);
    assertThat(value(controller, 1)).startsWith(1, 12, 1, 1, 10, 2, 4, 1, 2, 3, 4, 3);
  }

  @Test
  public void colorTemperatureIsLimitedToTheCharacteristicRange() throws Exception {
    AdaptiveLightingController controller =
        new AdaptiveLightingController(
            brightness,
            new ColorTemperatureCharacteristic(
                260, 500, () -> CompletableFuture.completedFuture(300), v -> {}, c -> {}, () -> {}),
            scheduler,
            handler,
            null);
    write(controller, tlv(0x02, tlv(0x01, TransitionTest.configuration())));

    runUpdate();

    verify(handler).applyColorTemperature(260);
  }

  @Test
  public void readingTheConfigurationReturnsTheOneReceived() throws Exception {
    AdaptiveLightingController controller = controller(null);
    write(controller, tlv(0x02, tlv(0x01, TransitionTest.configuration())));

    byte[] response = write(controller, tlv(0x01, tlv(0x01, new byte[] {10})));

    assertThat(response).isEqualTo(tlv(0x01, TransitionTest.configuration()));
  }

  @Test
  public void disablingStopsTheUpdates() throws Exception {
    AdaptiveLightingController controller = controller(null);
    write(controller, tlv(0x02, tlv(0x01, TransitionTest.configuration())));

    byte[] response = write(controller, tlv(0x02, tlv(0x01, tlv(0x01, new byte[] {10}))));

    assertThat(response).containsExactly(2, 0);
    verify(handler).saveState(isNull());
    verify(updates).cancel(false);
    assertThat(characteristic(controller, 2).getValue().join()).isEqualTo(0);
    assertThat(value(controller, 1)).isEmpty();
  }

  @Test
  public void savedStateIsRestoredUntilTheCurveEnds() {
    byte[] state = Transition.parse(TransitionTest.configuration(), 0).toState();
    AdaptiveLightingController controller = controller(state);

    assertThat(characteristic(controller, 2).getValue().join()).isEqualTo(1);
    runUpdate();
    verify(handler).saveState(isNull());
  }

  private AdaptiveLightingController controller(byte[] savedState) {
    colorTemperature.setInstanceId(10);
    return new AdaptiveLightingController(
        brightness, colorTemperature, scheduler, handler, savedState);
  }

  @SuppressWarnings("unchecked")
  private static <T extends BaseCharacteristic<?>> T characteristic(
      AdaptiveLightingController controller, int index) {
    return (T) controller.getCharacteristics().get(index);
  }

  private static byte[] value(AdaptiveLightingController controller, int index) {
    BaseCharacteristic<String> characteristic = characteristic(controller, index);
    return Base64.getDecoder().decode(characteristic.getValue().join());
  }

  private static byte[] write(AdaptiveLightingController controller, byte[] request)
      throws Exception {
    JsonArray value =
        Json.createArrayBuilder().add(Base64.getEncoder().encodeToString(request)).build();
    JsonObjectBuilder response = Json.createObjectBuilder();
    WriteResponseCharacteristic control = characteristic(controller, 1);
    control.setValueWithResponse(value.get(0), null, response);
    return Base64.getDecoder().decode(response.build().getString("value"));
  }

  private void runUpdate() {
    ArgumentCaptor<Runnable> update = ArgumentCaptor.forClass(Runnable.class);
    verify(scheduler)
        .scheduleWithFixedDelay(update.capture(), eq(0L), eq(60_000L), eq(TimeUnit.MILLISECONDS));
    update.getValue().run();
  }

  private static byte[] tlv(int type, byte[] value) {
    ByteArrayOutputStream out = new ByteArrayOutputStream();
    Tlv8.write(out, type, value);
    return out.toByteArray();
  }
}
