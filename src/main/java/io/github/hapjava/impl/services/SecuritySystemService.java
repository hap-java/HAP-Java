package io.github.hapjava.impl.services;

import io.github.hapjava.accessories.SecuritySystem;
import io.github.hapjava.impl.characteristics.securitysystem.CurrentSecuritySystemStateCharacteristic;
import io.github.hapjava.impl.characteristics.securitysystem.SecuritySystemAlarmTypeCharacteristic;
import io.github.hapjava.impl.characteristics.securitysystem.TargetSecuritySystemStateCharacteristic;

public class SecuritySystemService extends AbstractServiceImpl {

  public SecuritySystemService(SecuritySystem securitySystem) {
    this(securitySystem, securitySystem.getLabel());
  }

  public SecuritySystemService(SecuritySystem securitySystem, String serviceName) {
    super("0000007E-0000-1000-8000-0026BB765291", securitySystem, serviceName);
    addCharacteristic(new CurrentSecuritySystemStateCharacteristic(securitySystem));
    addCharacteristic(new TargetSecuritySystemStateCharacteristic(securitySystem));
    addCharacteristic(new SecuritySystemAlarmTypeCharacteristic(securitySystem));
  }
}
