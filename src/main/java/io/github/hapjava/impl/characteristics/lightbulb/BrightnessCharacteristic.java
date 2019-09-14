package io.github.hapjava.impl.characteristics.lightbulb;

import io.github.hapjava.HomekitCharacteristicChangeCallback;
import io.github.hapjava.accessories.DimmableLightbulb;
import io.github.hapjava.characteristics.EventableCharacteristic;
import io.github.hapjava.characteristics.IntegerCharacteristic;
import java.util.concurrent.CompletableFuture;

public class BrightnessCharacteristic extends IntegerCharacteristic
    implements EventableCharacteristic {

  private final DimmableLightbulb lightbulb;

  public BrightnessCharacteristic(DimmableLightbulb lightbulb) {
    super(
        "00000008-0000-1000-8000-0026BB765291",
        true,
        true,
        "Adjust brightness of the light",
        0,
        100,
        "%");
    this.lightbulb = lightbulb;
  }

  @Override
  public void subscribe(HomekitCharacteristicChangeCallback callback) {
    lightbulb.subscribeBrightness(callback);
  }

  @Override
  public void unsubscribe() {
    lightbulb.unsubscribeBrightness();
  }

  @Override
  protected void setValue(Integer value) throws Exception {
    lightbulb.setBrightness(value);
  }

  @Override
  protected CompletableFuture<Integer> getValue() {
    return lightbulb.getBrightness();
  }
}
