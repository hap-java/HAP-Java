package com.beowulfe.hap.impl.services;

import com.beowulfe.hap.accessories.Valve;
import com.beowulfe.hap.accessories.ValveWithTimer;
import com.beowulfe.hap.impl.characteristics.common.ActiveCharacteristic;
import com.beowulfe.hap.impl.characteristics.common.InUseCharacteristic;
import com.beowulfe.hap.impl.characteristics.common.RemainingDurationCharacteristic;
import com.beowulfe.hap.impl.characteristics.valve.SetDurationCharacteristic;
import com.beowulfe.hap.impl.characteristics.valve.ValveTypeCharacteristic;

public class ValveService extends AbstractServiceImpl {

  public ValveService(Valve valve) {
    this(valve, valve.getLabel());
  }

  public ValveService(Valve valve, String serviceName) {
    super("000000D0-0000-1000-8000-0026BB765291", valve, serviceName);
    addCharacteristic(
        new ActiveCharacteristic(
            valve::getValveActive,
            a -> valve.setValveActive(a),
            valve::subscribeValveActive,
            valve::unsubscribeValveActive));
    addCharacteristic(new ValveTypeCharacteristic(valve));
    addCharacteristic(
        new InUseCharacteristic(
            valve::getValveInUse, valve::subscribeValveInUse, valve::unsubscribeValveInUse));

    if (valve instanceof ValveWithTimer) {
      ValveWithTimer vwt = (ValveWithTimer) valve;
      addCharacteristic(new SetDurationCharacteristic(vwt));
      addCharacteristic(
          new RemainingDurationCharacteristic(
              vwt::getRemainingDuration,
              vwt::subscribeRemainingDuration,
              vwt::unsubscribeRemainingDuration));
    }
  }
}
