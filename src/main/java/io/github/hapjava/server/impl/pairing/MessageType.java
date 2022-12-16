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
  SIGNATURE(0x0a),
  PERMISSIONS(0x0b),
  FRAGMENT_DATA(0x0c),
  FRAGMENT_LAST(0x0d),
  FLAGS(0x13),
  SEPARATOR(0xff);

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
