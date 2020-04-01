package io.github.hapjava.impl.json;

import io.github.hapjava.HomekitAccessory;
import io.github.hapjava.Service;
import io.github.hapjava.characteristics.Characteristic;
import io.github.hapjava.impl.HomekitRegistry;
import io.github.hapjava.impl.http.HttpResponse;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
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

    Map<HomekitAccessory, Map<Object, Integer>> idsByAccessory = new HashMap<>();
    for (HomekitAccessory accessory : registry.getAccessories()) {
      Map<Object, Integer> ids = idsByAccessory.computeIfAbsent(accessory, k -> new HashMap<>());
      int iid = 0;
      for (Service service : registry.getServices(accessory.getId())) {
        ids.put(service, ++iid);
        for (Characteristic characteristic : service.getCharacteristics()) {
          ids.put(characteristic, ++iid);
        }
      }
    }
    Map<Integer, List<CompletableFuture<JsonObject>>> accessoryServiceFutures = new HashMap<>();
    for (HomekitAccessory accessory : registry.getAccessories()) {
      List<CompletableFuture<JsonObject>> serviceFutures = new ArrayList<>();
      Map<Object, Integer> ids = idsByAccessory.get(accessory);
      for (Service service : registry.getServices(accessory.getId())) {
        serviceFutures.add(toJson(ids, service));
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

  private CompletableFuture<JsonObject> toJson(Map<Object, Integer> ids, Service service)
      throws Exception {
    String shortType =
        service.getType().replaceAll("^0*([0-9a-fA-F]+)-0000-1000-8000-0026BB765291$", "$1");
    JsonObjectBuilder builder =
        Json.createObjectBuilder().add("iid", ids.get(service)).add("type", shortType);
    List<Characteristic> characteristics = service.getCharacteristics();
    Collection<CompletableFuture<JsonObject>> characteristicFutures =
        new ArrayList<>(characteristics.size());
    for (Characteristic characteristic : characteristics) {
      characteristicFutures.add(characteristic.toJson(ids.get(characteristic)));
    }
    Set<Integer> linkedServiceIds =
        registry.getLinkedServices(service).stream().map(ids::get).collect(Collectors.toSet());

    return CompletableFuture.allOf(
            characteristicFutures.toArray(new CompletableFuture<?>[characteristicFutures.size()]))
        .thenApply(
            v -> {
              JsonArrayBuilder jsonCharacteristics = Json.createArrayBuilder();
              characteristicFutures.stream()
                  .map(future -> future.join())
                  .forEach(c -> jsonCharacteristics.add(c));
              builder.add("characteristics", jsonCharacteristics);
              if (!linkedServiceIds.isEmpty()) {
                builder.add("primary", true);
                JsonArrayBuilder jsonLinkedServices = Json.createArrayBuilder();
                linkedServiceIds.forEach(jsonLinkedServices::add);
                builder.add("linked", jsonLinkedServices);
              }
              return builder.build();
            });
  }
}
