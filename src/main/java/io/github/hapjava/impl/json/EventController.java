package io.github.hapjava.impl.json;

import io.github.hapjava.characteristics.EventableCharacteristic;
import io.github.hapjava.impl.http.HttpResponse;
import java.io.ByteArrayOutputStream;
import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;

public class EventController {

  public HttpResponse getMessage(int accessoryId, int iid, EventableCharacteristic changed)
      throws Exception {
    JsonArrayBuilder characteristics = Json.createArrayBuilder();

    JsonObjectBuilder characteristicBuilder = Json.createObjectBuilder();
    characteristicBuilder.add("aid", accessoryId);
    characteristicBuilder.add("iid", iid);
    changed.supplyValue(characteristicBuilder);
    characteristics.add(characteristicBuilder.build());

    JsonObject data = Json.createObjectBuilder().add("characteristics", characteristics).build();

    try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
      Json.createWriter(baos).write(data);
      byte[] dataBytes = baos.toByteArray();

      return new EventResponse(dataBytes);
    }
  }
}
