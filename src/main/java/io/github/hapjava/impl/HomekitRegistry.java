package io.github.hapjava.impl;

import io.github.hapjava.HomekitAccessory;
import io.github.hapjava.Service;
import io.github.hapjava.characteristics.Characteristic;
import io.github.hapjava.impl.services.AccessoryInformationService;
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
  private final Map<Integer, HomekitAccessory> accessories;
  private final Map<Integer, List<HomekitAccessory>> linkedAccessories;
  private final Map<HomekitAccessory, List<Service>> services = new HashMap<>();
  private final Map<Service, List<Service>> linkedServices = new HashMap<>();
  private final Map<HomekitAccessory, Map<Integer, Characteristic>> characteristics =
      new HashMap<>();
  private boolean isAllowUnauthenticatedRequests = false;

  public HomekitRegistry(String label) {
    this.label = label;
    this.accessories = new ConcurrentHashMap<>();
    this.linkedAccessories = new ConcurrentHashMap<>();
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
        newServices.add(new AccessoryInformationService(accessory));
        Collection<Service> services = accessory.getServices();
        newServices.addAll(services);
        List<HomekitAccessory> linkedAccessories = this.linkedAccessories.get(accessory.getId());
        if (linkedAccessories != null) {
          for (HomekitAccessory linkedAccessory : linkedAccessories) {
            Collection<Service> linkedServices = linkedAccessory.getServices();
            for (Service service : services) {
              this.linkedServices
                  .computeIfAbsent(service, k -> new ArrayList<>())
                  .addAll(linkedServices);
            }
            newServices.addAll(linkedServices);
          }
        }
      } catch (Exception e) {
        logger.error("Could not instantiate services for accessory " + accessory.getLabel(), e);
        services.put(accessory, Collections.emptyList());
        continue;
      }
      Map<Integer, Characteristic> newCharacteristics = new HashMap<>();
      services.put(accessory, newServices);
      for (Service service : newServices) {
        iid++;
        for (Characteristic characteristic : service.getCharacteristics()) {
          newCharacteristics.put(++iid, characteristic);
        }
      }
      characteristics.put(accessory, newCharacteristics);
    }
  }

  public String getLabel() {
    return label;
  }

  public Collection<HomekitAccessory> getAccessories() {
    return accessories.values();
  }

  public List<Service> getServices(Integer aid) {
    return Collections.unmodifiableList(services.get(accessories.get(aid)));
  }

  public Map<Integer, Characteristic> getCharacteristics(Integer aid) {
    Map<Integer, Characteristic> characteristics = this.characteristics.get(accessories.get(aid));
    if (characteristics == null) {
      return Collections.emptyMap();
    }
    return Collections.unmodifiableMap(characteristics);
  }

  public void add(HomekitAccessory accessory) {
    accessories.put(accessory.getId(), accessory);
  }

  public void addLinked(HomekitAccessory primaryAccessory, HomekitAccessory linkedAccessory) {
    linkedAccessories
        .computeIfAbsent(primaryAccessory.getId(), k -> new ArrayList<>())
        .add(linkedAccessory);
  }

  public void remove(HomekitAccessory accessory) {
    accessories.remove(accessory.getId());
  }

  public boolean isAllowUnauthenticatedRequests() {
    return isAllowUnauthenticatedRequests;
  }

  public void setAllowUnauthenticatedRequests(boolean allow) {
    this.isAllowUnauthenticatedRequests = allow;
  }

  public List<Service> getLinkedServices(Service primaryService) {
    return linkedServices.getOrDefault(primaryService, Collections.emptyList());
  }
}
