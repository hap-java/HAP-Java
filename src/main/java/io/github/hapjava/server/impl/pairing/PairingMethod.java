package io.github.hapjava.server.impl.pairing;

public enum PairingMethod {
  PAIR_SETUP(0),
  PAIR_SETUP_WITH_AUTH(1),
  PAIR_VERIFY(2),
  ADD_PAIRING(3),
  REMOVE_PAIRING(4),
  LIST_PAIRINGS(5);

  private final byte value;

  PairingMethod(byte value) {
    this.value = value;
  }

  PairingMethod(int value) {
    this.value = (byte) value;
  }

  public byte getValue() {
    return value;
  }
}
