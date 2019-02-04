package com.beowulfe.hap.impl.characteristics.lightbulb;

import com.beowulfe.hap.HomekitCharacteristicChangeCallback;
import com.beowulfe.hap.accessories.ColorfulLightbulb;
import com.beowulfe.hap.characteristics.EventableCharacteristic;
import com.beowulfe.hap.characteristics.FloatCharacteristic;
import java.util.concurrent.CompletableFuture;

public class HueCharacteristic extends FloatCharacteristic implements EventableCharacteristic {

  private final ColorfulLightbulb lightbulb;

  public HueCharacteristic(ColorfulLightbulb lightbulb) {
    super(
        "00000013-0000-1000-8000-0026BB765291",
        true,
        true,
        "Adjust hue of the light",
        0,
        360,
        1,
        "arcdegrees");
    this.lightbulb = lightbulb;
  }

  @Override
  protected void setValue(Double value) throws Exception {
    lightbulb.setHue(value);
  }

  @Override
  protected CompletableFuture<Double> getDoubleValue() {
    return lightbulb.getHue();
  }

  @Override
  public void subscribe(HomekitCharacteristicChangeCallback callback) {
    lightbulb.subscribeHue(callback);
  }

  @Override
  public void unsubscribe() {
    lightbulb.unsubscribeHue();
  }
}
