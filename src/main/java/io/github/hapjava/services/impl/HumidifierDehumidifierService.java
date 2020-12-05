package io.github.hapjava.services.impl;

import io.github.hapjava.accessories.HumidifierDehumidifierAccessory;
import io.github.hapjava.accessories.optionalcharacteristic.AccessoryWithHumidityDehumidifierThreshold;
import io.github.hapjava.accessories.optionalcharacteristic.AccessoryWithHumidityHumidifierThreshold;
import io.github.hapjava.accessories.optionalcharacteristic.AccessoryWithName;
import io.github.hapjava.accessories.optionalcharacteristic.AccessoryWithPhysicalControlsLock;
import io.github.hapjava.accessories.optionalcharacteristic.AccessoryWithRotationSpeed;
import io.github.hapjava.accessories.optionalcharacteristic.AccessoryWithSwingMode;
import io.github.hapjava.accessories.optionalcharacteristic.AccessoryWithWaterLevel;
import io.github.hapjava.characteristics.impl.common.ActiveCharacteristic;
import io.github.hapjava.characteristics.impl.common.ActiveEnum;
import io.github.hapjava.characteristics.impl.common.NameCharacteristic;
import io.github.hapjava.characteristics.impl.common.WaterLavelCharacteristic;
import io.github.hapjava.characteristics.impl.fan.LockPhysicalControlsCharacteristic;
import io.github.hapjava.characteristics.impl.fan.RotationSpeedCharacteristic;
import io.github.hapjava.characteristics.impl.fan.SwingModeCharacteristic;
import io.github.hapjava.characteristics.impl.humidifier.CurrentHumidifierDehumidifierStateCharacteristic;
import io.github.hapjava.characteristics.impl.humidifier.HumidityDehumidifierThresholdCharacteristic;
import io.github.hapjava.characteristics.impl.humidifier.HumidityHumidifierThresholdCharacteristic;
import io.github.hapjava.characteristics.impl.humidifier.TargetHumidifierDehumidifierStateCharacteristic;
import io.github.hapjava.characteristics.impl.humiditysensor.CurrentRelativeHumidityCharacteristic;

/** This service can be used to describe an air humidifier or/and an air dehumidifier. */
public class HumidifierDehumidifierService extends AbstractServiceImpl {

  public HumidifierDehumidifierService(
      ActiveCharacteristic activeCharacteristic,
      CurrentRelativeHumidityCharacteristic currentRelativeHumidityCharacteristic,
      CurrentHumidifierDehumidifierStateCharacteristic
          currentHumidifierDehumidifierStateCharacteristic,
      TargetHumidifierDehumidifierStateCharacteristic
          targetHumidifierDehumidifierStateCharacteristic) {
    super("000000BD-0000-1000-8000-0026BB765291");
    addCharacteristic(activeCharacteristic);
    addCharacteristic(currentRelativeHumidityCharacteristic);
    addCharacteristic(currentHumidifierDehumidifierStateCharacteristic);
    addCharacteristic(targetHumidifierDehumidifierStateCharacteristic);
  }

  public HumidifierDehumidifierService(HumidifierDehumidifierAccessory accessory) {
    this(
        new ActiveCharacteristic(
            () -> accessory.isActive().thenApply(s -> s ? ActiveEnum.ACTIVE : ActiveEnum.INACTIVE),
            (v) -> accessory.setActive(v == ActiveEnum.ACTIVE),
            accessory::subscribeActive,
            accessory::unsubscribeActive),
        new CurrentRelativeHumidityCharacteristic(
            accessory::getCurrentHumidity,
            accessory::subscribeCurrentHumidity,
            accessory::unsubscribeCurrentHumidity),
        new CurrentHumidifierDehumidifierStateCharacteristic(
            accessory::getCurrentHumidifierDehumidifierState,
            accessory::subscribeCurrentHumidifierDehumidifierState,
            accessory::unsubscribeCurrentHumidifierDehumidifierState),
        new TargetHumidifierDehumidifierStateCharacteristic(
            accessory.getTargetHumidifierDehumidifierStateValidValues(),
            accessory::getTargetHumidifierDehumidifierState,
            accessory::setTargetHumidifierDehumidifierState,
            accessory::subscribeTargetHumidifierDehumidifierState,
            accessory::unsubscribeTargetHumidifierDehumidifierState));
    if (accessory instanceof AccessoryWithName) {
      addOptionalCharacteristic(new NameCharacteristic(((AccessoryWithName) accessory)::getName));
    }
    if (accessory instanceof AccessoryWithRotationSpeed) {
      addOptionalCharacteristic(
          new RotationSpeedCharacteristic(
              ((AccessoryWithRotationSpeed) accessory)::getRotationSpeed,
              ((AccessoryWithRotationSpeed) accessory)::setRotationSpeed,
              ((AccessoryWithRotationSpeed) accessory)::subscribeRotationSpeed,
              ((AccessoryWithRotationSpeed) accessory)::unsubscribeRotationSpeed));
    }
    if (accessory instanceof AccessoryWithSwingMode) {
      addOptionalCharacteristic(
          new SwingModeCharacteristic(
              ((AccessoryWithSwingMode) accessory)::getSwingMode,
              ((AccessoryWithSwingMode) accessory)::setSwingMode,
              ((AccessoryWithSwingMode) accessory)::subscribeSwingMode,
              ((AccessoryWithSwingMode) accessory)::unsubscribeSwingMode));
    }
    if (accessory instanceof AccessoryWithPhysicalControlsLock) {
      addOptionalCharacteristic(
          new LockPhysicalControlsCharacteristic(
              ((AccessoryWithPhysicalControlsLock) accessory)::getLockControls,
              ((AccessoryWithPhysicalControlsLock) accessory)::setLockControls,
              ((AccessoryWithPhysicalControlsLock) accessory)::subscribeLockControls,
              ((AccessoryWithPhysicalControlsLock) accessory)::unsubscribeLockControls));
    }
    if (accessory instanceof AccessoryWithHumidityDehumidifierThreshold) {
      addOptionalCharacteristic(
          new HumidityDehumidifierThresholdCharacteristic(
              ((AccessoryWithHumidityDehumidifierThreshold) accessory)::getHumidityThreshold,
              ((AccessoryWithHumidityDehumidifierThreshold) accessory)::setHumidityThreshold,
              ((AccessoryWithHumidityDehumidifierThreshold) accessory)::subscribeHumidityThreshold,
              ((AccessoryWithHumidityDehumidifierThreshold) accessory)
                  ::unsubscribeHumidityThreshold));
    }
    if (accessory instanceof AccessoryWithHumidityHumidifierThreshold) {
      addOptionalCharacteristic(
          new HumidityHumidifierThresholdCharacteristic(
              ((AccessoryWithHumidityHumidifierThreshold) accessory)::getHumidityThreshold,
              ((AccessoryWithHumidityHumidifierThreshold) accessory)::setHumidityThreshold,
              ((AccessoryWithHumidityHumidifierThreshold) accessory)::subscribeHumidityThreshold,
              ((AccessoryWithHumidityHumidifierThreshold) accessory)
                  ::unsubscribeHumidityThreshold));
    }
    if (accessory instanceof AccessoryWithWaterLevel) {
      addOptionalCharacteristic(
          new WaterLavelCharacteristic(
              ((AccessoryWithWaterLevel) accessory)::getWaterLevel,
              ((AccessoryWithWaterLevel) accessory)::subscribeWaterLevel,
              ((AccessoryWithWaterLevel) accessory)::unsubscribeWaterLevel));
    }
  }

  public void addOptionalCharacteristic(NameCharacteristic name) {
    addCharacteristic(name);
  }

  public void addOptionalCharacteristic(RotationSpeedCharacteristic speed) {
    addCharacteristic(speed);
  }

  public void addOptionalCharacteristic(SwingModeCharacteristic mode) {
    addCharacteristic(mode);
  }

  public void addOptionalCharacteristic(LockPhysicalControlsCharacteristic lock) {
    addCharacteristic(lock);
  }

  public void addOptionalCharacteristic(HumidityDehumidifierThresholdCharacteristic threshold) {
    addCharacteristic(threshold);
  }

  public void addOptionalCharacteristic(HumidityHumidifierThresholdCharacteristic threshold) {
    addCharacteristic(threshold);
  }

  public void addOptionalCharacteristic(WaterLavelCharacteristic level) {
    addCharacteristic(level);
  }
}
