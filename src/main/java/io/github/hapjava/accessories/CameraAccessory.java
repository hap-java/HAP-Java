package io.github.hapjava.accessories;

import io.github.hapjava.characteristics.HomekitCharacteristicChangeCallback;
import io.github.hapjava.services.Service;
import io.github.hapjava.services.impl.CameraControlService;
import io.github.hapjava.services.impl.CameraRTPService;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface CameraAccessory extends HomekitAccessory {
  /**
   * Retrieves the current binary state of the light.
   *
   * @return a future that will contain the binary state
   */
  CompletableFuture<Boolean> getCameraActiveState();

  /**
   * Sets the binary state of the light
   *
   * @param activeState the binary state to set
   * @return a future that completes when the change is made
   * @throws Exception when the change cannot be made
   */
  CompletableFuture<Void> setCameraActiveState(boolean activeState) throws Exception;

  /**
   * Subscribes to changes in the binary state of the light.
   *
   * @param callback the function to call when the state changes.
   */
  void subscribeCameraActiveState(HomekitCharacteristicChangeCallback callback);

  /** Unsubscribes from changes in the binary state of the light. */
  void unsubscribeCameraActiveState();

  List<CameraRTPService> getCameraRTPServices();

  @Override
  default Collection<Service> getServices() {
    ArrayList<Service> services = new ArrayList<>();
    services.add(new CameraControlService(this));
    services.addAll(getCameraRTPServices());
    return Collections.unmodifiableList(services);
  }
}
