package com.beowulfe.hap.impl.characteristics.garage;

import com.beowulfe.hap.HomekitCharacteristicChangeCallback;
import com.beowulfe.hap.accessories.GarageDoor;
import com.beowulfe.hap.accessories.properties.DoorState;
import com.beowulfe.hap.characteristics.EnumCharacteristic;
import com.beowulfe.hap.characteristics.EventableCharacteristic;
import java.util.concurrent.CompletableFuture;

public class CurrentDoorStateCharacteristic extends EnumCharacteristic
    implements EventableCharacteristic {

  private final GarageDoor door;

  public CurrentDoorStateCharacteristic(GarageDoor door) {
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
