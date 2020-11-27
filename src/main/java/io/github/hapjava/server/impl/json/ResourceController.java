package io.github.hapjava.server.impl.json;

import static io.github.hapjava.server.impl.http.HttpMethod.POST;

import io.github.hapjava.accessories.HomekitAccessory;
import io.github.hapjava.server.impl.HomekitRegistry;
import io.github.hapjava.server.impl.http.HomekitClientConnection;
import io.github.hapjava.server.impl.http.HttpRequest;
import io.github.hapjava.server.impl.http.HttpResponse;
import io.github.hapjava.server.impl.responses.NotFoundResponse;
import io.github.hapjava.server.impl.responses.OkResponse;
import io.github.hapjava.services.impl.ResourceService;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import javax.json.Json;
import javax.json.JsonObject;

public class ResourceController {
  private HomekitRegistry registry;

  public ResourceController(HomekitRegistry registry) {
    this.registry = registry;
  }

  private static CompletableFuture<HttpResponse> notFound() {
    return CompletableFuture.completedFuture(new NotFoundResponse());
  }

  private static final Map<String, String> imageHeaders =
      Collections.unmodifiableMap(
          new HashMap<String, String>() {
            private static final long serialVersionUID = 1L;

            {
              put("Content-type", "image/jpeg");
            }
          });

  public CompletableFuture<HttpResponse> handle(
      HttpRequest request, HomekitClientConnection connection) throws IOException {
    if (POST != request.getMethod()) {
      return notFound();
    }

    try (ByteArrayInputStream bais = new ByteArrayInputStream(request.getBody())) {
      JsonObject json = Json.createReader(bais).readObject();

      int aid = json.getInt("aid");
      String resourceType = json.getString("resource-type");
      HomekitAccessory accessory =
          registry.getAccessories().stream()
              .filter(homekitAccessory -> homekitAccessory.getId() == aid)
              .findFirst()
              .orElse(null);
      if (accessory == null) {
        return notFound();
      }

      ResourceService resourceService =
          accessory.getServices().stream()
              .filter(service -> service instanceof ResourceService)
              .map(service -> (ResourceService) service)
              .filter(service -> service.getResourceType().equals(resourceType))
              .findFirst()
              .orElse(null);

      if (resourceService == null) {
        return notFound();
      }

      CompletableFuture<byte[]> data =
          resourceService.handle(json.getInt("image-width"), json.getInt("image-height"));
      if (data == null) {
        return notFound();
      }

      return data.thenApply(
          bytes -> {
            OkResponse ok =
                new OkResponse(bytes) {
                  @Override
                  public Map<String, String> getHeaders() {
                    return imageHeaders;
                  }
                };
            return ok;
          });
    }
  }
}
