package io.github.hapjava.services.impl;

import io.github.hapjava.characteristics.impl.common.ServiceLabelNamespaceCharacteristic;

/** This service describes label scheme. */
public class ServiceLabelService extends AbstractServiceImpl {

  public ServiceLabelService(
      ServiceLabelNamespaceCharacteristic serviceLabelNamespaceCharacteristic) {
    super("000000CC-0000-1000-8000-0026BB765291");
    addCharacteristic(serviceLabelNamespaceCharacteristic);
  }
}
