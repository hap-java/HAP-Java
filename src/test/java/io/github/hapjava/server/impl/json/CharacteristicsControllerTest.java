package io.github.hapjava.server.impl.json;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import io.github.hapjava.characteristics.Characteristic;
import io.github.hapjava.characteristics.WriteResponseCharacteristic;
import io.github.hapjava.server.impl.HomekitRegistry;
import io.github.hapjava.server.impl.connections.SubscriptionManager;
import io.github.hapjava.server.impl.http.HomekitClientConnection;
import io.github.hapjava.server.impl.http.HttpRequest;
import io.github.hapjava.server.impl.http.HttpResponse;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import javax.json.JsonObjectBuilder;
import javax.json.JsonValue;
import org.junit.Before;
import org.junit.Test;

public class CharacteristicsControllerTest {

  private final HomekitRegistry registry = mock(HomekitRegistry.class);
  private final WriteResponseCharacteristic control = mock(WriteResponseCharacteristic.class);
  private final Characteristic on = mock(Characteristic.class);
  private final HomekitClientConnection connection = mock(HomekitClientConnection.class);
  private final CharacteristicsController controller =
      new CharacteristicsController(registry, mock(SubscriptionManager.class));

  @Before
  public void setUp() throws Exception {
    Map<Integer, Characteristic> characteristics = new HashMap<>();
    characteristics.put(5, control);
    characteristics.put(6, on);
    when(registry.getCharacteristics(1)).thenReturn(characteristics);
    when(connection.getUsername()).thenReturn("user");
    doAnswer(
            invocation -> {
              invocation.<JsonObjectBuilder>getArgument(2).add("value", "Ag==");
              return null;
            })
        .when(control)
        .setValueWithResponse(any(), any(), any());
  }

  @Test
  public void writeWithResponseRequestIsAnsweredWithMultiStatus() throws Exception {
    HttpResponse response =
        put(
            "{\"characteristics\":[{\"aid\":1,\"iid\":5,\"value\":\"AQ==\",\"r\":true},"
                + "{\"aid\":1,\"iid\":6,\"value\":true}]}");

    assertThat(response.getStatusCode()).isEqualTo(207);
    assertThat(body(response))
        .isEqualTo(
            "{\"characteristics\":[{\"aid\":1,\"iid\":5,\"status\":0,\"value\":\"Ag==\"},"
                + "{\"aid\":1,\"iid\":6,\"status\":0}]}");
    verify(on).setValue(JsonValue.TRUE, "user");
  }

  @Test
  public void writeWithoutResponseRequestHasNoContent() throws Exception {
    HttpResponse response = put("{\"characteristics\":[{\"aid\":1,\"iid\":5,\"value\":\"AQ==\"}]}");

    assertThat(response.getStatusCode()).isEqualTo(204);
    verify(control).setValue(any(JsonValue.class), eq("user"));
  }

  private HttpResponse put(String body) throws Exception {
    HttpRequest request = mock(HttpRequest.class);
    when(request.getBody()).thenReturn(body.getBytes(StandardCharsets.UTF_8));
    return controller.put(request, connection);
  }

  private static String body(HttpResponse response) {
    ByteBuffer buffer = response.getBody();
    byte[] bytes = new byte[buffer.remaining()];
    buffer.get(bytes);
    return new String(bytes, StandardCharsets.UTF_8);
  }
}
