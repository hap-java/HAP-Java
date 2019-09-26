package io.github.hapjava.impl.characteristics.garage;

import io.github.hapjava.HomekitCharacteristicChangeCallback;
import io.github.hapjava.accessories.GarageDoor;
import io.github.hapjava.accessories.properties.DoorState;
import io.github.hapjava.characteristics.EnumCharacteristic;
import io.github.hapjava.characteristics.EventableCharacteristic;
import java.util.concurrent.CompletableFuture;

public class TargetDoorStateCharacteristic extends EnumCharacteristic
    implements EventableCharacteristic {

  private final GarageDoor door;

  public TargetDoorStateCharacteristic(GarageDoor door) {
    super("00000032-0000-1000-8000-0026BB765291", true, true, "Target Door State", 1);
    this.door = door;
  }

  @Override
  protected void setValue(Integer value) throws Exception {
    door.setTargetDoorState(DoorState.fromCode(value));
  }

  @Override
  protected CompletableFuture<Integer> getValue() {
    return door.getTargetDoorState().thenApply(s -> s.getCode());
  }

  @Override
  public void subscribe(HomekitCharacteristicChangeCallback callback) {
    door.subscribeTargetDoorState(callback);
  }

  @Override
  public void unsubscribe() {
    door.unsubscribeTargetDoorState();
  }
}
