package com.beowulfe.hap.impl.accessories;

import com.beowulfe.hap.HomekitAccessory;

public interface Bridge extends HomekitAccessory {

  @Override
  default void identify() {}
}
