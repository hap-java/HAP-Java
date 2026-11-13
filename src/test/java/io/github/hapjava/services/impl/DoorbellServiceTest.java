package io.github.hapjava.services.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.withSettings;

import io.github.hapjava.accessories.DoorbellAccessory;
import io.github.hapjava.accessories.optionalcharacteristic.AccessoryWithMute;
import io.github.hapjava.characteristics.Characteristic;
import io.github.hapjava.characteristics.impl.audio.MuteCharacteristic;
import io.github.hapjava.characteristics.impl.common.ProgrammableSwitchEventCharacteristic;
import java.util.concurrent.CompletableFuture;
import javax.json.JsonObject;
import javax.json.JsonValue;
import org.junit.Test;

public class DoorbellServiceTest {

  @Test
  public void doorbellWithoutOptionalsOnlyHasSwitchEvent() {
    DoorbellService service = new DoorbellService(mock(DoorbellAccessory.class));

    assertThat(service.getType()).isEqualTo("00000121-0000-1000-8000-0026BB765291");
    assertThat(service.getCharacteristics())
        .hasExactlyElementsOfTypes(ProgrammableSwitchEventCharacteristic.class);
  }

  @Test
  public void doorbellWithMuteHasMuteCharacteristic() throws Exception {
    DoorbellAccessory accessory =
        mock(DoorbellAccessory.class, withSettings().extraInterfaces(AccessoryWithMute.class));
    when(((AccessoryWithMute) accessory).isMuted())
        .thenReturn(CompletableFuture.completedFuture(true));

    DoorbellService service = new DoorbellService(accessory);

    assertThat(service.getCharacteristics())
        .hasExactlyElementsOfTypes(
            ProgrammableSwitchEventCharacteristic.class, MuteCharacteristic.class);

    Characteristic mute = service.getCharacteristics().get(1);
    JsonObject json = mute.toJson(2).join();
    assertThat(json.getString("type")).isEqualTo("11A");
    assertThat(json.getBoolean("value")).isTrue();

    mute.setValue(JsonValue.FALSE);
    verify((AccessoryWithMute) accessory).setMute(false);
  }
}
