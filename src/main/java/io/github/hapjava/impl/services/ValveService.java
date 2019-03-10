package io.github.hapjava.impl.services;

import io.github.hapjava.accessories.Valve;
import io.github.hapjava.accessories.ValveWithTimer;
import io.github.hapjava.impl.characteristics.common.ActiveCharacteristic;
import io.github.hapjava.impl.characteristics.common.InUseCharacteristic;
import io.github.hapjava.impl.characteristics.common.RemainingDurationCharacteristic;
import io.github.hapjava.impl.characteristics.valve.SetDurationCharacteristic;
import io.github.hapjava.impl.characteristics.valve.ValveTypeCharacteristic;

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
