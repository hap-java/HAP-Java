package io.github.hapjava.impl.accessories;

import io.github.hapjava.HomekitAccessory;

public interface Bridge extends HomekitAccessory {

  @Override
  default void identify() {}
}
