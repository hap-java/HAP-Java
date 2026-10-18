package io.github.hapjava.characteristics.impl;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.hapjava.characteristics.Characteristic;
import io.github.hapjava.characteristics.impl.airpurifier.CurrentAirPurifierCharacteristic;
import io.github.hapjava.characteristics.impl.airpurifier.CurrentAirPurifierStateEnum;
import io.github.hapjava.characteristics.impl.airpurifier.TargetAirPurifierStateCharacteristic;
import io.github.hapjava.characteristics.impl.airpurifier.TargetAirPurifierStateEnum;
import io.github.hapjava.characteristics.impl.airquality.AirQualityCharacteristic;
import io.github.hapjava.characteristics.impl.airquality.AirQualityEnum;
import io.github.hapjava.characteristics.impl.battery.ChargingStateCharacteristic;
import io.github.hapjava.characteristics.impl.battery.ChargingStateEnum;
import io.github.hapjava.characteristics.impl.common.ActiveCharacteristic;
import io.github.hapjava.characteristics.impl.common.ActiveEnum;
import io.github.hapjava.characteristics.impl.common.ProgramModeCharacteristic;
import io.github.hapjava.characteristics.impl.common.ProgramModeEnum;
import io.github.hapjava.characteristics.impl.common.ServiceLabelNamespaceCharacteristic;
import io.github.hapjava.characteristics.impl.common.ServiceLabelNamespaceEnum;
import io.github.hapjava.characteristics.impl.fan.CurrentFanStateCharacteristic;
import io.github.hapjava.characteristics.impl.fan.CurrentFanStateEnum;
import io.github.hapjava.characteristics.impl.fan.LockPhysicalControlsCharacteristic;
import io.github.hapjava.characteristics.impl.fan.LockPhysicalControlsEnum;
import io.github.hapjava.characteristics.impl.fan.RotationDirectionCharacteristic;
import io.github.hapjava.characteristics.impl.fan.RotationDirectionEnum;
import io.github.hapjava.characteristics.impl.fan.SwingModeCharacteristic;
import io.github.hapjava.characteristics.impl.fan.SwingModeEnum;
import io.github.hapjava.characteristics.impl.fan.TargetFanStateCharacteristic;
import io.github.hapjava.characteristics.impl.fan.TargetFanStateEnum;
import io.github.hapjava.characteristics.impl.garagedoor.CurrentDoorStateCharacteristic;
import io.github.hapjava.characteristics.impl.garagedoor.CurrentDoorStateEnum;
import io.github.hapjava.characteristics.impl.garagedoor.TargetDoorStateCharacteristic;
import io.github.hapjava.characteristics.impl.garagedoor.TargetDoorStateEnum;
import io.github.hapjava.characteristics.impl.humidifier.CurrentHumidifierDehumidifierStateCharacteristic;
import io.github.hapjava.characteristics.impl.humidifier.CurrentHumidifierDehumidifierStateEnum;
import io.github.hapjava.characteristics.impl.inputsource.CurrentVisibilityStateCharacteristic;
import io.github.hapjava.characteristics.impl.inputsource.CurrentVisibilityStateEnum;
import io.github.hapjava.characteristics.impl.inputsource.InputDeviceTypeCharacteristic;
import io.github.hapjava.characteristics.impl.inputsource.InputDeviceTypeEnum;
import io.github.hapjava.characteristics.impl.inputsource.InputSourceTypeCharacteristic;
import io.github.hapjava.characteristics.impl.inputsource.InputSourceTypeEnum;
import io.github.hapjava.characteristics.impl.inputsource.TargetVisibilityStateCharacteristic;
import io.github.hapjava.characteristics.impl.inputsource.TargetVisibilityStateEnum;
import io.github.hapjava.characteristics.impl.lock.LockCurrentStateCharacteristic;
import io.github.hapjava.characteristics.impl.lock.LockCurrentStateEnum;
import io.github.hapjava.characteristics.impl.lock.LockTargetStateCharacteristic;
import io.github.hapjava.characteristics.impl.lock.LockTargetStateEnum;
import io.github.hapjava.characteristics.impl.slat.CurrentSlatStateCharacteristic;
import io.github.hapjava.characteristics.impl.slat.CurrentSlatStateEnum;
import io.github.hapjava.characteristics.impl.slat.SlatTypeCharacteristic;
import io.github.hapjava.characteristics.impl.slat.SlatTypeEnum;
import io.github.hapjava.characteristics.impl.television.ClosedCaptionsCharacteristic;
import io.github.hapjava.characteristics.impl.television.ClosedCaptionsEnum;
import io.github.hapjava.characteristics.impl.television.CurrentMediaStateCharacteristic;
import io.github.hapjava.characteristics.impl.television.CurrentMediaStateEnum;
import io.github.hapjava.characteristics.impl.television.PictureModeCharacteristic;
import io.github.hapjava.characteristics.impl.television.PictureModeEnum;
import io.github.hapjava.characteristics.impl.television.PowerModeCharacteristic;
import io.github.hapjava.characteristics.impl.television.PowerModeEnum;
import io.github.hapjava.characteristics.impl.television.RemoteKeyCharacteristic;
import io.github.hapjava.characteristics.impl.television.RemoteKeyEnum;
import io.github.hapjava.characteristics.impl.television.SleepDiscoveryModeCharacteristic;
import io.github.hapjava.characteristics.impl.television.SleepDiscoveryModeEnum;
import io.github.hapjava.characteristics.impl.television.TargetMediaStateCharacteristic;
import io.github.hapjava.characteristics.impl.television.TargetMediaStateEnum;
import io.github.hapjava.characteristics.impl.televisionspeaker.VolumeControlTypeCharacteristic;
import io.github.hapjava.characteristics.impl.televisionspeaker.VolumeControlTypeEnum;
import io.github.hapjava.characteristics.impl.televisionspeaker.VolumeSelectorCharacteristic;
import io.github.hapjava.characteristics.impl.televisionspeaker.VolumeSelectorEnum;
import io.github.hapjava.characteristics.impl.thermostat.TemperatureDisplayUnitCharacteristic;
import io.github.hapjava.characteristics.impl.thermostat.TemperatureDisplayUnitEnum;
import io.github.hapjava.characteristics.impl.valve.ValveTypeCharacteristic;
import io.github.hapjava.characteristics.impl.valve.ValveTypeEnum;
import io.github.hapjava.characteristics.impl.windowcovering.PositionStateCharacteristic;
import io.github.hapjava.characteristics.impl.windowcovering.PositionStateEnum;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import javax.json.Json;
import org.junit.Test;

/** Custom valid values support, see https://github.com/hap-java/HAP-Java/issues/163 */
public class CustomValidValuesCharacteristicsTest {

  @Test
  public void defaultConstructorsKeepTheirSerializedOutput() {
    // Serialized output captured before custom valid values were added.
    for (Case c :
        Arrays.asList(
            Case.of(
                new CurrentAirPurifierCharacteristic(
                    () -> CompletableFuture.completedFuture(CurrentAirPurifierStateEnum.INACTIVE),
                    cb -> {},
                    () -> {}),
                "{\"iid\":7,\"type\":\"A9\",\"perms\":[\"pr\",\"ev\"],\"format\":\"int\",\"value\":0,\"valid-values\":[0,1,2]}"),
            Case.of(
                new TargetAirPurifierStateCharacteristic(
                    () -> CompletableFuture.completedFuture(TargetAirPurifierStateEnum.MANUAL),
                    v -> {},
                    cb -> {},
                    () -> {}),
                "{\"iid\":7,\"type\":\"A8\",\"perms\":[\"pr\",\"pw\",\"ev\"],\"format\":\"int\",\"value\":0,\"valid-values\":[0,1]}"),
            Case.of(
                new AirQualityCharacteristic(
                    () -> CompletableFuture.completedFuture(AirQualityEnum.UNKNOWN),
                    cb -> {},
                    () -> {}),
                "{\"iid\":7,\"type\":\"95\",\"perms\":[\"pr\",\"ev\"],\"format\":\"int\",\"value\":0,\"valid-values\":[0,1,2,3,4,5]}"),
            Case.of(
                new ChargingStateCharacteristic(
                    () -> CompletableFuture.completedFuture(ChargingStateEnum.NOT_CHARGING),
                    cb -> {},
                    () -> {}),
                "{\"iid\":7,\"type\":\"8F\",\"perms\":[\"pr\",\"ev\"],\"format\":\"int\",\"value\":0,\"valid-values\":[0,1,2]}"),
            Case.of(
                new ActiveCharacteristic(
                    () -> CompletableFuture.completedFuture(ActiveEnum.INACTIVE),
                    v -> {},
                    cb -> {},
                    () -> {}),
                "{\"iid\":7,\"type\":\"B0\",\"perms\":[\"pr\",\"pw\",\"ev\"],\"format\":\"int\",\"value\":0,\"valid-values\":[0,1]}"),
            Case.of(
                new ProgramModeCharacteristic(
                    () -> CompletableFuture.completedFuture(ProgramModeEnum.NO_SCHEDULED),
                    cb -> {},
                    () -> {}),
                "{\"iid\":7,\"type\":\"D1\",\"perms\":[\"pr\",\"ev\"],\"format\":\"int\",\"value\":0,\"valid-values\":[0,1,2]}"),
            Case.of(
                new ServiceLabelNamespaceCharacteristic(
                    () -> CompletableFuture.completedFuture(ServiceLabelNamespaceEnum.DOTS)),
                "{\"iid\":7,\"type\":\"CD\",\"perms\":[\"pr\"],\"format\":\"int\",\"value\":0,\"valid-values\":[0,1]}"),
            Case.of(
                new CurrentFanStateCharacteristic(
                    () -> CompletableFuture.completedFuture(CurrentFanStateEnum.INACTIVE),
                    cb -> {},
                    () -> {}),
                "{\"iid\":7,\"type\":\"AF\",\"perms\":[\"pr\",\"ev\"],\"format\":\"int\",\"value\":0,\"valid-values\":[0,1,2]}"),
            Case.of(
                new LockPhysicalControlsCharacteristic(
                    () ->
                        CompletableFuture.completedFuture(
                            LockPhysicalControlsEnum.CONTROL_LOCK_DISABLED),
                    v -> {},
                    cb -> {},
                    () -> {}),
                "{\"iid\":7,\"type\":\"A7\",\"perms\":[\"pr\",\"pw\",\"ev\"],\"format\":\"int\",\"value\":0,\"valid-values\":[0,1]}"),
            Case.of(
                new RotationDirectionCharacteristic(
                    () -> CompletableFuture.completedFuture(RotationDirectionEnum.CLOCKWISE),
                    v -> {},
                    cb -> {},
                    () -> {}),
                "{\"iid\":7,\"type\":\"28\",\"perms\":[\"pr\",\"pw\",\"ev\"],\"format\":\"int\",\"value\":0,\"valid-values\":[0,1]}"),
            Case.of(
                new SwingModeCharacteristic(
                    () -> CompletableFuture.completedFuture(SwingModeEnum.SWING_DISABLED),
                    v -> {},
                    cb -> {},
                    () -> {}),
                "{\"iid\":7,\"type\":\"B6\",\"perms\":[\"pr\",\"pw\",\"ev\"],\"format\":\"int\",\"value\":0,\"valid-values\":[0,1]}"),
            Case.of(
                new TargetFanStateCharacteristic(
                    () -> CompletableFuture.completedFuture(TargetFanStateEnum.MANUAL),
                    v -> {},
                    cb -> {},
                    () -> {}),
                "{\"iid\":7,\"type\":\"BF\",\"perms\":[\"pr\",\"pw\",\"ev\"],\"format\":\"int\",\"value\":0,\"valid-values\":[0,1]}"),
            Case.of(
                new CurrentDoorStateCharacteristic(
                    () -> CompletableFuture.completedFuture(CurrentDoorStateEnum.OPEN),
                    cb -> {},
                    () -> {}),
                "{\"iid\":7,\"type\":\"E\",\"perms\":[\"pr\",\"ev\"],\"format\":\"int\",\"value\":0,\"valid-values\":[0,1,2,3,4]}"),
            Case.of(
                new TargetDoorStateCharacteristic(
                    () -> CompletableFuture.completedFuture(TargetDoorStateEnum.OPEN),
                    v -> {},
                    cb -> {},
                    () -> {}),
                "{\"iid\":7,\"type\":\"32\",\"perms\":[\"pr\",\"pw\",\"ev\"],\"format\":\"int\",\"value\":0,\"valid-values\":[0,1]}"),
            Case.of(
                new CurrentHumidifierDehumidifierStateCharacteristic(
                    () ->
                        CompletableFuture.completedFuture(
                            CurrentHumidifierDehumidifierStateEnum.INACTIVE),
                    cb -> {},
                    () -> {}),
                "{\"iid\":7,\"type\":\"B3\",\"perms\":[\"pr\",\"ev\"],\"format\":\"int\",\"value\":0,\"valid-values\":[0,1,2,3]}"),
            Case.of(
                new CurrentVisibilityStateCharacteristic(
                    () -> CompletableFuture.completedFuture(CurrentVisibilityStateEnum.SHOWN),
                    cb -> {},
                    () -> {}),
                "{\"iid\":7,\"type\":\"135\",\"perms\":[\"pr\",\"ev\"],\"format\":\"int\",\"value\":0,\"valid-values\":[0,1,2,3]}"),
            Case.of(
                new InputDeviceTypeCharacteristic(
                    () -> CompletableFuture.completedFuture(InputDeviceTypeEnum.OTHER),
                    cb -> {},
                    () -> {}),
                "{\"iid\":7,\"type\":\"DC\",\"perms\":[\"pr\",\"ev\"],\"format\":\"int\",\"value\":0,\"valid-values\":[0,1,2,3,4,5]}"),
            Case.of(
                new InputSourceTypeCharacteristic(
                    () -> CompletableFuture.completedFuture(InputSourceTypeEnum.OTHER),
                    cb -> {},
                    () -> {}),
                "{\"iid\":7,\"type\":\"DB\",\"perms\":[\"pr\",\"ev\"],\"format\":\"int\",\"value\":0,\"valid-values\":[0,1,2,3,4,5,6,7,8,9,10]}"),
            Case.of(
                new TargetVisibilityStateCharacteristic(
                    () -> CompletableFuture.completedFuture(TargetVisibilityStateEnum.SHOWN),
                    v -> {},
                    cb -> {},
                    () -> {}),
                "{\"iid\":7,\"type\":\"134\",\"perms\":[\"pr\",\"pw\",\"ev\"],\"format\":\"int\",\"value\":0,\"valid-values\":[0,1]}"),
            Case.of(
                new LockCurrentStateCharacteristic(
                    () -> CompletableFuture.completedFuture(LockCurrentStateEnum.UNSECURED),
                    cb -> {},
                    () -> {}),
                "{\"iid\":7,\"type\":\"1D\",\"perms\":[\"pr\",\"ev\"],\"format\":\"int\",\"value\":0,\"valid-values\":[0,1,2,3]}"),
            Case.of(
                new LockTargetStateCharacteristic(
                    () -> CompletableFuture.completedFuture(LockTargetStateEnum.UNSECURED),
                    v -> {},
                    cb -> {},
                    () -> {}),
                "{\"iid\":7,\"type\":\"1E\",\"perms\":[\"pr\",\"pw\",\"ev\"],\"format\":\"int\",\"value\":0,\"valid-values\":[0,1]}"),
            Case.of(
                new CurrentSlatStateCharacteristic(
                    () -> CompletableFuture.completedFuture(CurrentSlatStateEnum.FIXED),
                    cb -> {},
                    () -> {}),
                "{\"iid\":7,\"type\":\"AA\",\"perms\":[\"pr\",\"ev\"],\"format\":\"int\",\"value\":0,\"valid-values\":[0,1,2]}"),
            Case.of(
                new SlatTypeCharacteristic(
                    () -> CompletableFuture.completedFuture(SlatTypeEnum.HORIZONTAL)),
                "{\"iid\":7,\"type\":\"C0\",\"perms\":[\"pr\"],\"format\":\"int\",\"value\":0,\"valid-values\":[0,1]}"),
            Case.of(
                new ClosedCaptionsCharacteristic(
                    () -> CompletableFuture.completedFuture(ClosedCaptionsEnum.DISABLED),
                    v -> {},
                    cb -> {},
                    () -> {}),
                "{\"iid\":7,\"type\":\"DD\",\"perms\":[\"pr\",\"pw\",\"ev\"],\"format\":\"int\",\"value\":0,\"valid-values\":[0,1]}"),
            Case.of(
                new CurrentMediaStateCharacteristic(
                    () -> CompletableFuture.completedFuture(CurrentMediaStateEnum.PLAY),
                    cb -> {},
                    () -> {}),
                "{\"iid\":7,\"type\":\"E0\",\"perms\":[\"pr\",\"ev\"],\"format\":\"int\",\"value\":0,\"valid-values\":[0,1,2,3]}"),
            Case.of(
                new PictureModeCharacteristic(
                    () -> CompletableFuture.completedFuture(PictureModeEnum.OTHER),
                    v -> {},
                    cb -> {},
                    () -> {}),
                "{\"iid\":7,\"type\":\"E2\",\"perms\":[\"pr\",\"pw\",\"ev\"],\"format\":\"int\",\"value\":0,\"valid-values\":[0,1,2,3,4,5,6,7]}"),
            Case.of(
                new PowerModeCharacteristic(v -> {}),
                "{\"iid\":7,\"type\":\"DF\",\"perms\":[\"pw\"],\"format\":\"int\",\"valid-values\":[0,1]}"),
            Case.of(
                new RemoteKeyCharacteristic(v -> {}),
                "{\"iid\":7,\"type\":\"E1\",\"perms\":[\"pw\"],\"format\":\"int\",\"valid-values\":[0,1,2,3,4,5,6,7,8,9,10,11,15]}"),
            Case.of(
                new SleepDiscoveryModeCharacteristic(
                    () ->
                        CompletableFuture.completedFuture(SleepDiscoveryModeEnum.NOT_DISCOVERABLE),
                    cb -> {},
                    () -> {}),
                "{\"iid\":7,\"type\":\"E8\",\"perms\":[\"pr\",\"ev\"],\"format\":\"int\",\"value\":0,\"valid-values\":[0,1]}"),
            Case.of(
                new TargetMediaStateCharacteristic(
                    () -> CompletableFuture.completedFuture(TargetMediaStateEnum.PLAY),
                    v -> {},
                    cb -> {},
                    () -> {}),
                "{\"iid\":7,\"type\":\"137\",\"perms\":[\"pr\",\"pw\",\"ev\"],\"format\":\"int\",\"value\":0,\"valid-values\":[0,1,2]}"),
            Case.of(
                new VolumeControlTypeCharacteristic(
                    () -> CompletableFuture.completedFuture(VolumeControlTypeEnum.NONE),
                    cb -> {},
                    () -> {}),
                "{\"iid\":7,\"type\":\"E9\",\"perms\":[\"pr\",\"ev\"],\"format\":\"int\",\"value\":0,\"valid-values\":[0,1,2,3]}"),
            Case.of(
                new VolumeSelectorCharacteristic(v -> {}),
                "{\"iid\":7,\"type\":\"EA\",\"perms\":[\"pw\"],\"format\":\"int\",\"valid-values\":[0,1]}"),
            Case.of(
                new TemperatureDisplayUnitCharacteristic(
                    () -> CompletableFuture.completedFuture(TemperatureDisplayUnitEnum.CELSIUS),
                    v -> {},
                    cb -> {},
                    () -> {}),
                "{\"iid\":7,\"type\":\"36\",\"perms\":[\"pr\",\"pw\",\"ev\"],\"format\":\"int\",\"value\":0,\"valid-values\":[0,1]}"),
            Case.of(
                new ValveTypeCharacteristic(
                    () -> CompletableFuture.completedFuture(ValveTypeEnum.GENERIC),
                    cb -> {},
                    () -> {}),
                "{\"iid\":7,\"type\":\"D5\",\"perms\":[\"pr\",\"ev\"],\"format\":\"int\",\"value\":0,\"valid-values\":[0,1,2,3]}"),
            Case.of(
                new PositionStateCharacteristic(
                    () -> CompletableFuture.completedFuture(PositionStateEnum.DECREASING),
                    cb -> {},
                    () -> {}),
                "{\"iid\":7,\"type\":\"72\",\"perms\":[\"pr\",\"ev\"],\"format\":\"int\",\"value\":0,\"valid-values\":[0,1,2]}"))) {
      assertThat(c.characteristic.toJson(7).join().toString())
          .as(c.characteristic.getClass().getSimpleName())
          .isEqualTo(c.expected);
    }
  }

  @Test
  public void customValidValuesAreAdvertised() {
    for (Case c :
        Arrays.asList(
            Case.of(
                new CurrentAirPurifierCharacteristic(
                    new CurrentAirPurifierStateEnum[] {CurrentAirPurifierStateEnum.PURIFYING_AIR},
                    () -> CompletableFuture.completedFuture(CurrentAirPurifierStateEnum.INACTIVE),
                    cb -> {},
                    () -> {}),
                "[2]"),
            Case.of(
                new TargetAirPurifierStateCharacteristic(
                    new TargetAirPurifierStateEnum[] {TargetAirPurifierStateEnum.AUTO},
                    () -> CompletableFuture.completedFuture(TargetAirPurifierStateEnum.MANUAL),
                    v -> {},
                    cb -> {},
                    () -> {}),
                "[1]"),
            Case.of(
                new AirQualityCharacteristic(
                    new AirQualityEnum[] {AirQualityEnum.POOR},
                    () -> CompletableFuture.completedFuture(AirQualityEnum.UNKNOWN),
                    cb -> {},
                    () -> {}),
                "[5]"),
            Case.of(
                new ChargingStateCharacteristic(
                    new ChargingStateEnum[] {ChargingStateEnum.NOT_CHARABLE},
                    () -> CompletableFuture.completedFuture(ChargingStateEnum.NOT_CHARGING),
                    cb -> {},
                    () -> {}),
                "[2]"),
            Case.of(
                new ActiveCharacteristic(
                    new ActiveEnum[] {ActiveEnum.ACTIVE},
                    () -> CompletableFuture.completedFuture(ActiveEnum.INACTIVE),
                    v -> {},
                    cb -> {},
                    () -> {}),
                "[1]"),
            Case.of(
                new ProgramModeCharacteristic(
                    new ProgramModeEnum[] {ProgramModeEnum.SCHEDULED_MANUAL},
                    () -> CompletableFuture.completedFuture(ProgramModeEnum.NO_SCHEDULED),
                    cb -> {},
                    () -> {}),
                "[2]"),
            Case.of(
                new ServiceLabelNamespaceCharacteristic(
                    new ServiceLabelNamespaceEnum[] {ServiceLabelNamespaceEnum.ARABIC_NUMERALS},
                    () -> CompletableFuture.completedFuture(ServiceLabelNamespaceEnum.DOTS)),
                "[1]"),
            Case.of(
                new CurrentFanStateCharacteristic(
                    new CurrentFanStateEnum[] {CurrentFanStateEnum.BLOWING_AIR},
                    () -> CompletableFuture.completedFuture(CurrentFanStateEnum.INACTIVE),
                    cb -> {},
                    () -> {}),
                "[2]"),
            Case.of(
                new LockPhysicalControlsCharacteristic(
                    new LockPhysicalControlsEnum[] {LockPhysicalControlsEnum.CONTROL_LOCK_ENABLED},
                    () ->
                        CompletableFuture.completedFuture(
                            LockPhysicalControlsEnum.CONTROL_LOCK_DISABLED),
                    v -> {},
                    cb -> {},
                    () -> {}),
                "[1]"),
            Case.of(
                new RotationDirectionCharacteristic(
                    new RotationDirectionEnum[] {RotationDirectionEnum.COUNTER_CLOCKWISE},
                    () -> CompletableFuture.completedFuture(RotationDirectionEnum.CLOCKWISE),
                    v -> {},
                    cb -> {},
                    () -> {}),
                "[1]"),
            Case.of(
                new SwingModeCharacteristic(
                    new SwingModeEnum[] {SwingModeEnum.SWING_ENABLED},
                    () -> CompletableFuture.completedFuture(SwingModeEnum.SWING_DISABLED),
                    v -> {},
                    cb -> {},
                    () -> {}),
                "[1]"),
            Case.of(
                new TargetFanStateCharacteristic(
                    new TargetFanStateEnum[] {TargetFanStateEnum.AUTO},
                    () -> CompletableFuture.completedFuture(TargetFanStateEnum.MANUAL),
                    v -> {},
                    cb -> {},
                    () -> {}),
                "[1]"),
            Case.of(
                new CurrentDoorStateCharacteristic(
                    new CurrentDoorStateEnum[] {CurrentDoorStateEnum.STOPPED},
                    () -> CompletableFuture.completedFuture(CurrentDoorStateEnum.OPEN),
                    cb -> {},
                    () -> {}),
                "[4]"),
            Case.of(
                new TargetDoorStateCharacteristic(
                    new TargetDoorStateEnum[] {TargetDoorStateEnum.CLOSED},
                    () -> CompletableFuture.completedFuture(TargetDoorStateEnum.OPEN),
                    v -> {},
                    cb -> {},
                    () -> {}),
                "[1]"),
            Case.of(
                new CurrentHumidifierDehumidifierStateCharacteristic(
                    new CurrentHumidifierDehumidifierStateEnum[] {
                      CurrentHumidifierDehumidifierStateEnum.DEHUMIDIFYING
                    },
                    () ->
                        CompletableFuture.completedFuture(
                            CurrentHumidifierDehumidifierStateEnum.INACTIVE),
                    cb -> {},
                    () -> {}),
                "[3]"),
            Case.of(
                new CurrentVisibilityStateCharacteristic(
                    new CurrentVisibilityStateEnum[] {CurrentVisibilityStateEnum.UNKNOWN_2},
                    () -> CompletableFuture.completedFuture(CurrentVisibilityStateEnum.SHOWN),
                    cb -> {},
                    () -> {}),
                "[3]"),
            Case.of(
                new InputDeviceTypeCharacteristic(
                    new InputDeviceTypeEnum[] {InputDeviceTypeEnum.AUDIO_SYSTEM},
                    () -> CompletableFuture.completedFuture(InputDeviceTypeEnum.OTHER),
                    cb -> {},
                    () -> {}),
                "[5]"),
            Case.of(
                new InputSourceTypeCharacteristic(
                    new InputSourceTypeEnum[] {InputSourceTypeEnum.APPLICATION},
                    () -> CompletableFuture.completedFuture(InputSourceTypeEnum.OTHER),
                    cb -> {},
                    () -> {}),
                "[10]"),
            Case.of(
                new TargetVisibilityStateCharacteristic(
                    new TargetVisibilityStateEnum[] {TargetVisibilityStateEnum.HIDDEN},
                    () -> CompletableFuture.completedFuture(TargetVisibilityStateEnum.SHOWN),
                    v -> {},
                    cb -> {},
                    () -> {}),
                "[1]"),
            Case.of(
                new LockCurrentStateCharacteristic(
                    new LockCurrentStateEnum[] {LockCurrentStateEnum.UNKNOWN},
                    () -> CompletableFuture.completedFuture(LockCurrentStateEnum.UNSECURED),
                    cb -> {},
                    () -> {}),
                "[3]"),
            Case.of(
                new LockTargetStateCharacteristic(
                    new LockTargetStateEnum[] {LockTargetStateEnum.SECURED},
                    () -> CompletableFuture.completedFuture(LockTargetStateEnum.UNSECURED),
                    v -> {},
                    cb -> {},
                    () -> {}),
                "[1]"),
            Case.of(
                new CurrentSlatStateCharacteristic(
                    new CurrentSlatStateEnum[] {CurrentSlatStateEnum.SWINGING},
                    () -> CompletableFuture.completedFuture(CurrentSlatStateEnum.FIXED),
                    cb -> {},
                    () -> {}),
                "[2]"),
            Case.of(
                new SlatTypeCharacteristic(
                    new SlatTypeEnum[] {SlatTypeEnum.VERTICAL},
                    () -> CompletableFuture.completedFuture(SlatTypeEnum.HORIZONTAL)),
                "[1]"),
            Case.of(
                new ClosedCaptionsCharacteristic(
                    new ClosedCaptionsEnum[] {ClosedCaptionsEnum.ENABLED},
                    () -> CompletableFuture.completedFuture(ClosedCaptionsEnum.DISABLED),
                    v -> {},
                    cb -> {},
                    () -> {}),
                "[1]"),
            Case.of(
                new CurrentMediaStateCharacteristic(
                    new CurrentMediaStateEnum[] {CurrentMediaStateEnum.UNKNOWN},
                    () -> CompletableFuture.completedFuture(CurrentMediaStateEnum.PLAY),
                    cb -> {},
                    () -> {}),
                "[3]"),
            Case.of(
                new PictureModeCharacteristic(
                    new PictureModeEnum[] {PictureModeEnum.CUSTOM},
                    () -> CompletableFuture.completedFuture(PictureModeEnum.OTHER),
                    v -> {},
                    cb -> {},
                    () -> {}),
                "[7]"),
            Case.of(
                new PowerModeCharacteristic(new PowerModeEnum[] {PowerModeEnum.HIDE}, v -> {}),
                "[1]"),
            Case.of(
                new RemoteKeyCharacteristic(new RemoteKeyEnum[] {RemoteKeyEnum.INFO}, v -> {}),
                "[15]"),
            Case.of(
                new SleepDiscoveryModeCharacteristic(
                    new SleepDiscoveryModeEnum[] {SleepDiscoveryModeEnum.ALWAYS_DISCOVERABLE},
                    () ->
                        CompletableFuture.completedFuture(SleepDiscoveryModeEnum.NOT_DISCOVERABLE),
                    cb -> {},
                    () -> {}),
                "[1]"),
            Case.of(
                new TargetMediaStateCharacteristic(
                    new TargetMediaStateEnum[] {TargetMediaStateEnum.STOP},
                    () -> CompletableFuture.completedFuture(TargetMediaStateEnum.PLAY),
                    v -> {},
                    cb -> {},
                    () -> {}),
                "[2]"),
            Case.of(
                new VolumeControlTypeCharacteristic(
                    new VolumeControlTypeEnum[] {VolumeControlTypeEnum.ABSOLUTE},
                    () -> CompletableFuture.completedFuture(VolumeControlTypeEnum.NONE),
                    cb -> {},
                    () -> {}),
                "[3]"),
            Case.of(
                new VolumeSelectorCharacteristic(
                    new VolumeSelectorEnum[] {VolumeSelectorEnum.DECREMENT}, v -> {}),
                "[1]"),
            Case.of(
                new TemperatureDisplayUnitCharacteristic(
                    new TemperatureDisplayUnitEnum[] {TemperatureDisplayUnitEnum.FAHRENHEIT},
                    () -> CompletableFuture.completedFuture(TemperatureDisplayUnitEnum.CELSIUS),
                    v -> {},
                    cb -> {},
                    () -> {}),
                "[1]"),
            Case.of(
                new ValveTypeCharacteristic(
                    new ValveTypeEnum[] {ValveTypeEnum.WATER_FAUCET},
                    () -> CompletableFuture.completedFuture(ValveTypeEnum.GENERIC),
                    cb -> {},
                    () -> {}),
                "[3]"),
            Case.of(
                new PositionStateCharacteristic(
                    new PositionStateEnum[] {PositionStateEnum.STOPPED},
                    () -> CompletableFuture.completedFuture(PositionStateEnum.DECREASING),
                    cb -> {},
                    () -> {}),
                "[2]"))) {
      assertThat(c.characteristic.toJson(7).join().getJsonArray("valid-values").toString())
          .as(c.characteristic.getClass().getSimpleName())
          .isEqualTo(c.expected);
    }
  }

  @Test
  public void writesOutsideCustomValidValuesAreIgnored() {
    List<TemperatureDisplayUnitEnum> written = new ArrayList<>();
    TemperatureDisplayUnitCharacteristic characteristic =
        new TemperatureDisplayUnitCharacteristic(
            new TemperatureDisplayUnitEnum[] {TemperatureDisplayUnitEnum.CELSIUS},
            () -> CompletableFuture.completedFuture(TemperatureDisplayUnitEnum.CELSIUS),
            written::add,
            cb -> {},
            () -> {});

    characteristic.setValue(Json.createArrayBuilder().add(1).build().get(0));
    characteristic.setValue(Json.createArrayBuilder().add(0).build().get(0));

    assertThat(written).containsExactly(TemperatureDisplayUnitEnum.CELSIUS);
  }

  @Test
  public void defaultIsFirstCustomValidValue() {
    PositionStateCharacteristic characteristic =
        new PositionStateCharacteristic(
            new PositionStateEnum[] {PositionStateEnum.STOPPED, PositionStateEnum.DECREASING},
            () -> CompletableFuture.completedFuture(PositionStateEnum.STOPPED),
            cb -> {},
            () -> {});

    assertThat(characteristic.getDefault()).isEqualTo(PositionStateEnum.STOPPED.getCode());
  }

  private static class Case {
    final Characteristic characteristic;
    final String expected;

    private Case(Characteristic characteristic, String expected) {
      this.characteristic = characteristic;
      this.expected = expected;
    }

    static Case of(Characteristic characteristic, String expected) {
      return new Case(characteristic, expected);
    }
  }
}
