package io.github.hapjava.impl.characteristics.carbondioxide;

import com.beowulfe.hap.accessories.properties.CarbonDioxideDetectedState;
import io.github.hapjava.HomekitCharacteristicChangeCallback;
import io.github.hapjava.accessories.CarbonDioxideSensor;
import io.github.hapjava.characteristics.EnumCharacteristic;
import io.github.hapjava.characteristics.EventableCharacteristic;
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
