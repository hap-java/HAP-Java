package io.github.hapjava.server.impl;

import io.github.hapjava.accessories.Bridge;
import io.github.hapjava.services.Service;
import java.util.Collection;
import java.util.Collections;
import java.util.concurrent.CompletableFuture;

public class HomekitBridge implements Bridge {

  private final String label;
  private final String serialNumber;
  private final String model;
  private final String manufacturer;
  private final String firmwareRevision;
  private final String hardwareRevision;

  public HomekitBridge(
      String label,
      String serialNumber,
      String model,
      String manufacturer,
      String firmwareRevision,
      String hardwareRevision) {
    this.label = label;
    this.serialNumber = serialNumber;
    this.model = model;
    this.manufacturer = manufacturer;
    this.firmwareRevision = firmwareRevision;
    this.hardwareRevision = hardwareRevision;
  }

  @Override
  public CompletableFuture<String> getName() {
    return CompletableFuture.completedFuture(label);
  }

  @Override
  public CompletableFuture<String> getSerialNumber() {
    return CompletableFuture.completedFuture(serialNumber);
  }

  @Override
  public CompletableFuture<String> getModel() {
    return CompletableFuture.completedFuture(model);
  }

  @Override
  public CompletableFuture<String> getManufacturer() {
    return CompletableFuture.completedFuture(manufacturer);
  }

  @Override
  public CompletableFuture<String> getFirmwareRevision() {
    return CompletableFuture.completedFuture(firmwareRevision);
  }

  @Override
  public Collection<Service> getServices() {
    return Collections.emptyList();
  }

  @Override
  public int getId() {
    return 1;
  }
}
