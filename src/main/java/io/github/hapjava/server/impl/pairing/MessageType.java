package io.github.hapjava.server.impl.pairing;

public enum MessageType {
  METHOD(0),
  USERNAME(1),
  SALT(2),
  PUBLIC_KEY(3),
  PROOF(4),
  ENCRYPTED_DATA(5),
  STATE(6),
  ERROR(7),
  SIGNATURE(10);

  private final short key;

  MessageType(short key) {
    this.key = key;
  }

  MessageType(int key) {
    this.key = (short) key;
  }

  public short getKey() {
    return key;
  }
}
