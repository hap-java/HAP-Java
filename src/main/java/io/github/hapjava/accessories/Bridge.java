package io.github.hapjava.accessories;

public interface Bridge extends HomekitAccessory {

  @Override
  default void identify() {}
}
