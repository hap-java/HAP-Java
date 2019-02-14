package com.beowulfe.hap.accessories;

import com.beowulfe.hap.HomekitAccessory;
import com.beowulfe.hap.accessories.characteristics.LowBatteryStatus;
import java.util.Optional;

/**
 * A several characteristics that are shared among all Sensor type accesories
 *
 * @author Cody Cutrer initial contribution
 */
public interface AbstractSensor extends HomekitAccessory {
  /** returns the optional implementation of LowBatteryStatus */
  default Optional<LowBatteryStatus> getLowBatteryStatusCharacteristic() {
    Optional<LowBatteryStatus> result = Optional.empty();
    return result;
  }
}
