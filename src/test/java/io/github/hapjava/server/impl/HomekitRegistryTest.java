package io.github.hapjava.server.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import io.github.hapjava.accessories.LightbulbAccessory;
import io.github.hapjava.characteristics.Characteristic;
import io.github.hapjava.characteristics.impl.base.BaseCharacteristic;
import io.github.hapjava.server.impl.connections.SubscriptionManager;
import io.github.hapjava.services.impl.LightbulbService;
import java.util.Collections;
import java.util.Map;
import org.junit.Test;

public class HomekitRegistryTest {

  @Test
  public void characteristicsKnowTheirInstanceId() {
    LightbulbAccessory accessory = mock(LightbulbAccessory.class);
    when(accessory.getId()).thenReturn(2);
    when(accessory.getServices())
        .thenReturn(Collections.singletonList(new LightbulbService(accessory)));
    HomekitRegistry registry = new HomekitRegistry("test", mock(SubscriptionManager.class));
    registry.add(accessory);

    registry.reset();

    Map<Integer, Characteristic> characteristics = registry.getCharacteristics(2);
    assertThat(characteristics).isNotEmpty();
    characteristics.forEach(
        (iid, characteristic) ->
            assertThat(((BaseCharacteristic<?>) characteristic).getInstanceId()).isEqualTo(iid));
  }
}
