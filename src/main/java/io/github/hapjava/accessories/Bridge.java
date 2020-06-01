package io.github.hapjava.accessories;

/** HomeKit bridge service. */
public interface Bridge extends HomekitAccessory {

  @Override
  default void identify() {}
}
