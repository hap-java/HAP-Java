package io.github.hapjava.services.impl;

import io.github.hapjava.characteristics.impl.common.VersionCharacteristic;

/** This service describes label scheme. */
public class HAPProtocolInformationService extends AbstractServiceImpl {

  public HAPProtocolInformationService(VersionCharacteristic versionCharacteristic) {
    super("000000A2-0000-1000-8000-0026BB765291");
    addCharacteristic(versionCharacteristic);
  }
}
