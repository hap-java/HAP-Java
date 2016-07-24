package com.beowulfe.hap.impl.services;

import com.beowulfe.hap.accessories.SecuritySystem;
import com.beowulfe.hap.impl.characteristics.securitysystem.CurrentSecuritySystemStateCharacteristic;
import com.beowulfe.hap.impl.characteristics.securitysystem.SecuritySystemAlarmTypeCharacteristic;
import com.beowulfe.hap.impl.characteristics.securitysystem.TargetSecuritySystemStateCharacteristic;

public class SecuritySystemService extends AbstractServiceImpl {

    public SecuritySystemService(SecuritySystem securitySystem) {
        super("0000007E-0000-1000-8000-0026BB765291", securitySystem);
        addCharacteristic(new CurrentSecuritySystemStateCharacteristic(securitySystem));
        addCharacteristic(new TargetSecuritySystemStateCharacteristic(securitySystem));
        addCharacteristic(new SecuritySystemAlarmTypeCharacteristic(securitySystem));
    }
}
