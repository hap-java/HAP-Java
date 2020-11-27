package io.github.hapjava.services.impl;

import io.github.hapjava.accessories.CameraAccessory;
import io.github.hapjava.characteristics.impl.common.OnCharacteristic;

public class CameraControlService extends AbstractServiceImpl {
  public CameraControlService(OnCharacteristic onState) {
    super("00000111-0000-1000-8000-0026BB765291");
    addCharacteristic(onState);
  }

  public CameraControlService(CameraAccessory accessory) {
    this(
        new OnCharacteristic(
            accessory::getCameraActiveState,
            accessory::setCameraActiveState,
            accessory::subscribeCameraActiveState,
            accessory::unsubscribeCameraActiveState));
  }
}
