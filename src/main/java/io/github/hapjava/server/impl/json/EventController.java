package io.github.hapjava.server.impl.json;

import io.github.hapjava.characteristics.EventableCharacteristic;
import io.github.hapjava.server.impl.connections.PendingNotification;
import io.github.hapjava.server.impl.http.HttpResponse;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonWriter;

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
      JsonWriter writer = Json.createWriter(baos);
      writer.write(data);
      writer.close();
      byte[] dataBytes = baos.toByteArray();

      return new EventResponse(dataBytes);
    }
  }

  public HttpResponse getMessage(ArrayList<PendingNotification> notifications) throws Exception {
    JsonArrayBuilder characteristics = Json.createArrayBuilder();

    for (PendingNotification notification : notifications) {
      JsonObjectBuilder characteristicBuilder = Json.createObjectBuilder();
      characteristicBuilder.add("aid", notification.aid);
      characteristicBuilder.add("iid", notification.iid);
      notification.characteristic.supplyValue(characteristicBuilder);
      characteristics.add(characteristicBuilder.build());
    }

    JsonObject data = Json.createObjectBuilder().add("characteristics", characteristics).build();

    try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
      JsonWriter writer = Json.createWriter(baos);
      writer.write(data);
      writer.close();
      byte[] dataBytes = baos.toByteArray();

      return new EventResponse(dataBytes);
    }
  }
}
