package io.github.hapjava.impl.services;

import io.github.hapjava.accessories.Outlet;
import io.github.hapjava.impl.characteristics.common.PowerStateCharacteristic;
import io.github.hapjava.impl.characteristics.outlet.OutletInUseCharacteristic;

public class OutletService extends AbstractServiceImpl {

  public OutletService(Outlet outlet) {
    this(outlet, outlet.getLabel());
  }

  public OutletService(Outlet outlet, String serviceName) {
    super("00000047-0000-1000-8000-0026BB765291", outlet, serviceName);
    addCharacteristic(
        new PowerStateCharacteristic(
            () -> outlet.getPowerState(),
            v -> outlet.setPowerState(v),
            c -> outlet.subscribePowerState(c),
            () -> outlet.unsubscribePowerState()));
    addCharacteristic(new OutletInUseCharacteristic(outlet));
  }
}
