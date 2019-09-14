package com.beowulfe.hap.impl.characteristics.carbondioxide;

import com.beowulfe.hap.HomekitCharacteristicChangeCallback;
import com.beowulfe.hap.accessories.CarbonDioxideSensor;
import com.beowulfe.hap.accessories.properties.CarbonDioxideDetectedState;
import com.beowulfe.hap.characteristics.EnumCharacteristic;
import com.beowulfe.hap.characteristics.EventableCharacteristic;
import java.util.concurrent.CompletableFuture;

public class CarbonDioxideDetectedCharacteristic extends EnumCharacteristic
    implements EventableCharacteristic {

  private final CarbonDioxideSensor carbonDioxideSensor;

  public CarbonDioxideDetectedCharacteristic(CarbonDioxideSensor carbonDioxideSensor) {
    super("00000092-0000-1000-8000-0026BB765291", false, true, "Carbon Dioxide Detected", 1);
    this.carbonDioxideSensor = carbonDioxideSensor;
  }

  @Override
  protected CompletableFuture<Integer> getValue() {
    return carbonDioxideSensor
        .getCarbonDioxideDetectedState()
        .thenApply(CarbonDioxideDetectedState::getCode);
  }

  @Override
  protected void setValue(Integer value) throws Exception {
    // Read Only
  }

  @Override
  public void subscribe(HomekitCharacteristicChangeCallback callback) {
    carbonDioxideSensor.subscribeCarbonDioxideDetectedState(callback);
  }

  @Override
  public void unsubscribe() {
    carbonDioxideSensor.unsubscribeCarbonDioxideDetectedState();
  }
}
