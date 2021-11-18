package io.github.hapjava.server.impl.json;

import io.github.hapjava.accessories.HomekitAccessory;
import io.github.hapjava.characteristics.Characteristic;
import io.github.hapjava.server.impl.HomekitRegistry;
import io.github.hapjava.server.impl.http.HttpResponse;
import io.github.hapjava.services.Service;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import javax.json.*;

public class AccessoryController {

  private final HomekitRegistry registry;

  public AccessoryController(HomekitRegistry registry) {
    this.registry = registry;
  }

  public HttpResponse listing() throws Exception {
    JsonArrayBuilder accessories = Json.createArrayBuilder();

    Map<Integer, List<CompletableFuture<JsonObject>>> accessoryServiceFutures = new HashMap<>();
    for (HomekitAccessory accessory : registry.getAccessories()) {
      List<CompletableFuture<JsonObject>> serviceFutures = new ArrayList<>();

      Map<Integer, Service> servicesByInterfaceId = registry.getServices(accessory.getId());

      Map<Object, Integer> iidLookup = new HashMap<>();
      iidLookup.putAll(swapKeyAndValue(servicesByInterfaceId));
      iidLookup.putAll(swapKeyAndValue(registry.getCharacteristics(accessory.getId())));

      for (Service service : servicesByInterfaceId.values()) {
        serviceFutures.add(toJson(service, iidLookup));
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

    try (ByteArrayOutputStream baos = new ByteArrayOutputStream();
        JsonWriter jsonWriter = Json.createWriter(baos)) {
      jsonWriter.write(Json.createObjectBuilder().add("accessories", accessories).build());
      return new HapJsonResponse(baos.toByteArray());
    }
  }

  private CompletableFuture<JsonObject> toJson(Service service, Map<Object, Integer> iidLookup)
      throws Exception {
    String shortType =
        service.getType().replaceAll("^0*([0-9a-fA-F]+)-0000-1000-8000-0026BB765291$", "$1");
    JsonObjectBuilder builder =
        Json.createObjectBuilder().add("iid", iidLookup.get(service)).add("type", shortType);
    List<Characteristic> characteristics = service.getCharacteristics();
    Collection<CompletableFuture<JsonObject>> characteristicFutures =
        new ArrayList<>(characteristics.size());
    for (Characteristic characteristic : characteristics) {
      Integer iid = iidLookup.get(characteristic);
      characteristicFutures.add(characteristic.toJson(iid));
    }

    return CompletableFuture.allOf(
            characteristicFutures.toArray(new CompletableFuture<?>[characteristicFutures.size()]))
        .thenApply(
            v -> {
              JsonArrayBuilder jsonCharacteristics = Json.createArrayBuilder();
              characteristicFutures.stream()
                  .map(future -> future.join())
                  .forEach(c -> jsonCharacteristics.add(c));
              builder.add("characteristics", jsonCharacteristics);

              if (!service.getLinkedServices().isEmpty()) {
                JsonArrayBuilder jsonLinkedServices = Json.createArrayBuilder();
                service.getLinkedServices().stream()
                    .map(iidLookup::get)
                    .forEach(jsonLinkedServices::add);
                builder.add("linked", jsonLinkedServices);
              }

              return builder.build();
            });
  }

  private <K, V> Map<V, K> swapKeyAndValue(Map<K, V> map) {
    return map.entrySet().stream().collect(Collectors.toMap(Entry::getValue, Entry::getKey));
  }
}
