package io.github.hapjava.services.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.withSettings;

import io.github.hapjava.accessories.StatefulProgrammableSwitchAccessory;
import io.github.hapjava.accessories.optionalcharacteristic.AccessoryWithName;
import io.github.hapjava.characteristics.Characteristic;
import io.github.hapjava.characteristics.EventableCharacteristic;
import io.github.hapjava.characteristics.HomekitCharacteristicChangeCallback;
import io.github.hapjava.characteristics.impl.common.NameCharacteristic;
import io.github.hapjava.characteristics.impl.common.ProgrammableSwitchEnum;
import io.github.hapjava.characteristics.impl.common.ProgrammableSwitchEventCharacteristic;
import io.github.hapjava.characteristics.impl.common.ProgrammableSwitchOutputStateCharacteristic;
import io.github.hapjava.services.Service;
import java.util.Collection;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonString;
import org.junit.Before;
import org.junit.Test;

public class StatefulProgrammableSwitchServiceTest {

  private StatefulProgrammableSwitchAccessory accessory;

  @Before
  public void setup() throws Exception {
    accessory = mock(StatefulProgrammableSwitchAccessory.class);
    when(accessory.getSwitchEvent()).thenReturn(CompletableFuture.completedFuture(null));
    when(accessory.getSwitchOutputState()).thenReturn(CompletableFuture.completedFuture(1));
  }

  @Test
  public void serviceHasStatefulSwitchTypeAndRequiredCharacteristics() {
    StatefulProgrammableSwitchService service = new StatefulProgrammableSwitchService(accessory);

    assertThat(service.getType()).isEqualTo("00000088-0000-1000-8000-0026BB765291");
    assertThat(service.getCharacteristics())
        .hasSize(2)
        .hasExactlyElementsOfTypes(
            ProgrammableSwitchEventCharacteristic.class,
            ProgrammableSwitchOutputStateCharacteristic.class);
  }

  @Test
  public void nameIsAddedWhenAccessoryHasName() {
    StatefulProgrammableSwitchAccessory namedAccessory =
        mock(
            StatefulProgrammableSwitchAccessory.class,
            withSettings().extraInterfaces(AccessoryWithName.class));

    StatefulProgrammableSwitchService service =
        new StatefulProgrammableSwitchService(namedAccessory);

    assertThat(service.getCharacteristics())
        .hasSize(3)
        .last()
        .isInstanceOf(NameCharacteristic.class);
  }

  @Test
  public void defaultGetServicesReturnsStatefulSwitchService() {
    Collection<Service> services = new TestSwitch().getServices();

    assertThat(services).hasSize(1);
    assertThat(services.iterator().next()).isInstanceOf(StatefulProgrammableSwitchService.class);
  }

  @Test
  public void outputStateAdvertisesUint8WithRangeZeroToOne() {
    JsonObject json =
        outputState(new StatefulProgrammableSwitchService(accessory)).toJson(9).join();

    assertThat(json.getString("type")).isEqualTo("74");
    assertThat(json.getString("format")).isEqualTo("uint8");
    assertThat(
            json.getJsonArray("perms").getValuesAs(JsonString.class).stream()
                .map(JsonString::getString)
                .collect(Collectors.toList()))
        .containsExactly("pr", "pw", "ev");
    assertThat(json.getInt("value")).isEqualTo(1);
    assertThat(json.getInt("minValue")).isEqualTo(0);
    assertThat(json.getInt("maxValue")).isEqualTo(1);
    assertThat(json.getInt("minStep")).isEqualTo(1);
    assertThat(json.containsKey("unit")).isFalse();
  }

  @Test
  public void writingOutputStateCallsAccessorySetter() throws Exception {
    outputState(new StatefulProgrammableSwitchService(accessory))
        .setValue(Json.createArrayBuilder().add(1).build().get(0));

    verify(accessory).setSwitchOutputState(1);
  }

  @Test
  public void outputStateSubscriptionsAreDelegatedToAccessory() {
    EventableCharacteristic outputState =
        (EventableCharacteristic) outputState(new StatefulProgrammableSwitchService(accessory));
    HomekitCharacteristicChangeCallback callback = () -> {};

    outputState.subscribe(callback);
    outputState.unsubscribe();

    verify(accessory).subscribeSwitchOutputState(callback);
    verify(accessory).unsubscribeSwitchOutputState();
  }

  @Test
  public void switchEventStaysReadOnlyAndEventable() {
    JsonObject json =
        new StatefulProgrammableSwitchService(accessory)
            .getCharacteristics()
            .get(0)
            .toJson(8)
            .join();

    assertThat(json.getString("type")).isEqualTo("73");
    assertThat(json.getJsonArray("perms").toString()).isEqualTo("[\"pr\",\"ev\"]");

    ((EventableCharacteristic)
            new StatefulProgrammableSwitchService(accessory).getCharacteristics().get(0))
        .subscribe(() -> {});
    verify(accessory).subscribeSwitchEvent(any());
  }

  private static Characteristic outputState(StatefulProgrammableSwitchService service) {
    return service.getCharacteristics().get(1);
  }

  /** Minimal accessory, so the interface's default getServices() runs for real. */
  private static class TestSwitch implements StatefulProgrammableSwitchAccessory {
    @Override
    public int getId() {
      return 2;
    }

    @Override
    public CompletableFuture<String> getName() {
      return CompletableFuture.completedFuture("switch");
    }

    @Override
    public void identify() {}

    @Override
    public CompletableFuture<String> getSerialNumber() {
      return CompletableFuture.completedFuture("1");
    }

    @Override
    public CompletableFuture<String> getModel() {
      return CompletableFuture.completedFuture("model");
    }

    @Override
    public CompletableFuture<String> getManufacturer() {
      return CompletableFuture.completedFuture("maker");
    }

    @Override
    public CompletableFuture<String> getFirmwareRevision() {
      return CompletableFuture.completedFuture("1.0");
    }

    @Override
    public CompletableFuture<ProgrammableSwitchEnum> getSwitchEvent() {
      return CompletableFuture.completedFuture(null);
    }

    @Override
    public void subscribeSwitchEvent(HomekitCharacteristicChangeCallback callback) {}

    @Override
    public void unsubscribeSwitchEvent() {}

    @Override
    public CompletableFuture<Integer> getSwitchOutputState() {
      return CompletableFuture.completedFuture(0);
    }

    @Override
    public CompletableFuture<Void> setSwitchOutputState(int state) {
      return CompletableFuture.completedFuture(null);
    }

    @Override
    public void subscribeSwitchOutputState(HomekitCharacteristicChangeCallback callback) {}

    @Override
    public void unsubscribeSwitchOutputState() {}
  }
}
