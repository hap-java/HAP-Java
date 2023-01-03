package io.github.hapjava.server.impl;

import io.github.hapjava.accessories.HomekitAccessory;
import io.github.hapjava.characteristics.Characteristic;
import io.github.hapjava.server.impl.connections.SubscriptionManager;
import io.github.hapjava.services.Service;
import io.github.hapjava.services.impl.AccessoryInformationService;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HomekitRegistry {

  private static final Logger logger = LoggerFactory.getLogger(HomekitRegistry.class);

  private final String label;
  private final SubscriptionManager subscriptions;
  private final Map<Integer, HomekitAccessory> accessories;
  private HomekitAccessory primaryAccessory = null;
  private final Map<HomekitAccessory, Map<Integer, Service>> services = new HashMap<>();
  private final Map<HomekitAccessory, Map<Integer, Characteristic>> characteristics =
      new HashMap<>();
  private boolean isAllowUnauthenticatedRequests = false;

  public HomekitRegistry(String label, SubscriptionManager subscriptions) {
    this.label = label;
    this.subscriptions = subscriptions;
    this.accessories = new ConcurrentHashMap<>();
    reset();
  }

  public synchronized void reset() {
    characteristics.clear();
    services.clear();
    for (HomekitAccessory accessory : accessories.values()) {
      int iid = 0;
      List<Service> newServices;
      try {
        newServices = new ArrayList<>(2);
        Collection<Service> services = accessory.getServices();
        if (!services.stream().anyMatch(s -> s instanceof AccessoryInformationService)) {
          newServices.add(new AccessoryInformationService(accessory));
        }
        for (Service service : services) {
          newServices.add(service);
          newServices.addAll(service.getLinkedServices());
        }
      } catch (Exception e) {
        logger.warn("Could not instantiate services for accessory " + accessory.getName(), e);
        services.put(accessory, Collections.emptyMap());
        continue;
      }

      Map<Integer, Characteristic> newCharacteristicsByInterfaceId = new HashMap<>();
      Map<Integer, Service> newServicesByInterfaceId = new HashMap<>();
      for (Service service : newServices) {
        newServicesByInterfaceId.put(++iid, service);
        for (Characteristic characteristic : service.getCharacteristics()) {
          newCharacteristicsByInterfaceId.put(++iid, characteristic);
        }
      }
      services.put(accessory, newServicesByInterfaceId);
      characteristics.put(accessory, newCharacteristicsByInterfaceId);
    }
    subscriptions.resync(this);
  }

  public String getLabel() {
    return label;
  }

  public Collection<HomekitAccessory> getAccessories() {
    return accessories.values();
  }

  public HomekitAccessory getPrimaryAccessory() {
    return primaryAccessory;
  }

  public Map<Integer, Service> getServices(Integer aid) {
    return Collections.unmodifiableMap(services.get(accessories.get(aid)));
  }

  public Map<Integer, Characteristic> getCharacteristics(Integer aid) {
    Map<Integer, Characteristic> characteristics = this.characteristics.get(accessories.get(aid));
    if (characteristics == null) {
      return Collections.emptyMap();
    }
    return Collections.unmodifiableMap(characteristics);
  }

  public void add(HomekitAccessory accessory) {
    if (accessories.isEmpty()) {
      primaryAccessory = accessory;
    }
    accessories.put(accessory.getId(), accessory);
  }

  public boolean remove(HomekitAccessory accessory) {
    HomekitAccessory localPrimaryAccessory = primaryAccessory;
    if (localPrimaryAccessory != null && localPrimaryAccessory.getId() == accessory.getId()) {
      primaryAccessory = null;
    }
    return accessories.remove(accessory.getId()) != null;
  }

  public boolean isAllowUnauthenticatedRequests() {
    return isAllowUnauthenticatedRequests;
  }

  public void setAllowUnauthenticatedRequests(boolean allow) {
    this.isAllowUnauthenticatedRequests = allow;
  }
}
