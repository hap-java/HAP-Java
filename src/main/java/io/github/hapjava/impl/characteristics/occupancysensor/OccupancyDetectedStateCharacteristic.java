package io.github.hapjava.impl.characteristics.occupancysensor;

import io.github.hapjava.HomekitCharacteristicChangeCallback;
import io.github.hapjava.accessories.OccupancySensor;
import io.github.hapjava.characteristics.BooleanCharacteristic;
import io.github.hapjava.characteristics.EventableCharacteristic;
import java.util.concurrent.CompletableFuture;

public class OccupancyDetectedStateCharacteristic extends BooleanCharacteristic
    implements EventableCharacteristic {

  private final OccupancySensor occupancySensor;

  public OccupancyDetectedStateCharacteristic(OccupancySensor occupancySensor) {
    super("00000071-0000-1000-8000-0026BB765291", false, true, "Occupancy Detected");
    this.occupancySensor = occupancySensor;
  }

  @Override
  protected CompletableFuture<Boolean> getValue() {
    return occupancySensor.getOccupancyDetected();
  }

  @Override
  protected void setValue(Boolean value) throws Exception {
    // Read Only
  }

  @Override
  public void subscribe(HomekitCharacteristicChangeCallback callback) {
    occupancySensor.subscribeOccupancyDetected(callback);
  }

  @Override
  public void unsubscribe() {
    occupancySensor.unsubscribeOccupancyDetected();
  }
}
