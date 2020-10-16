package io.github.hapjava.services.impl;

import io.github.hapjava.characteristics.Characteristic;
import io.github.hapjava.services.Service;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

abstract class AbstractServiceImpl implements Service {
  private final Logger logger = LoggerFactory.getLogger(this.getClass());
  private final String type;
  private final List<Characteristic> characteristics = new LinkedList<>();
  private final List<Service> linkedServices = new LinkedList<>();

  /** @param type unique UUID of the service according to HAP specification. */
  public AbstractServiceImpl(String type) {
    this.type = type;
  }

  @Override
  public List<Characteristic> getCharacteristics() {
    return Collections.unmodifiableList(characteristics);
  }

  @Override
  public String getType() {
    return type;
  }

  @Override
  public List<Service> getLinkedServices() {
    return Collections.unmodifiableList(linkedServices);
  }

  public void addCharacteristic(Characteristic characteristic) {
    this.characteristics.add(characteristic);
  }

  public void addLinkedService(Service service) {
    this.linkedServices.add(service);
  }
}
