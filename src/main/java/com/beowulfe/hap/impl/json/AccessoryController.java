package com.beowulfe.hap.impl.json;

import com.beowulfe.hap.HomekitAccessory;
import com.beowulfe.hap.Service;
import com.beowulfe.hap.characteristics.Characteristic;
import com.beowulfe.hap.impl.HomekitRegistry;
import com.beowulfe.hap.impl.http.HttpResponse;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.CompletableFuture;
import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;

public class AccessoryController {

  private final HomekitRegistry registry;

  public AccessoryController(HomekitRegistry registry) {
    this.registry = registry;
  }

  public HttpResponse listing() throws Exception {
    JsonArrayBuilder accessories = Json.createArrayBuilder();

    Map<Integer, List<CompletableFuture<JsonObject>>> accessoryServiceFutures = new HashMap<>();
    for (HomekitAccessory accessory : registry.getAccessories()) {
      int iid = 0;
      List<CompletableFuture<JsonObject>> serviceFutures = new ArrayList<>();
      for (Service service : registry.getServices(accessory.getId())) {
        serviceFutures.add(toJson(service, iid));
        iid += service.getCharacteristics().size() + 1;
      }
      accessoryServiceFutures.put(accessory.getId(), serviceFutures);
    }

    Map<Integer, JsonArrayBuilder> serviceArrayBuilders = new HashMap<>();
    for (Entry<Integer, List<CompletableFuture<JsonObject>>> entry :
        accessoryServiceFutures.entrySet()) {
      JsonArrayBuilder arr = Json.createArrayBuilder();
      for (CompletableFuture<JsonObject> future : entry.getValue()) {
        arr.add(future.join());
      }
      serviceArrayBuilders.put(entry.getKey(), arr);
    }

    for (HomekitAccessory accessory : registry.getAccessories()) {
      accessories.add(
          Json.createObjectBuilder()
              .add("aid", accessory.getId())
              .add("services", serviceArrayBuilders.get(accessory.getId())));
    }

    try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
      Json.createWriter(baos)
          .write(Json.createObjectBuilder().add("accessories", accessories).build());
      return new HapJsonResponse(baos.toByteArray());
    }
  }

  private CompletableFuture<JsonObject> toJson(Service service, int interfaceId) throws Exception {
    JsonObjectBuilder builder =
        Json.createObjectBuilder().add("iid", ++interfaceId).add("type", service.getType());
    List<Characteristic> characteristics = service.getCharacteristics();
    Collection<CompletableFuture<JsonObject>> characteristicFutures =
        new ArrayList<>(characteristics.size());
    for (Characteristic characteristic : characteristics) {
      characteristicFutures.add(characteristic.toJson(++interfaceId));
    }

    return CompletableFuture.allOf(
            characteristicFutures.toArray(new CompletableFuture<?>[characteristicFutures.size()]))
        .thenApply(
            v -> {
              JsonArrayBuilder jsonCharacteristics = Json.createArrayBuilder();
              characteristicFutures
                  .stream()
                  .map(future -> future.join())
                  .forEach(c -> jsonCharacteristics.add(c));
              builder.add("characteristics", jsonCharacteristics);
              return builder.build();
            });
  }
}
