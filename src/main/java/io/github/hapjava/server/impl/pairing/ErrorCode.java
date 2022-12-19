package io.github.hapjava.server.impl.pairing;

public enum ErrorCode {
  OK(0),
  UNKNOWN(1),
  AUTHENTICATION(2),
  BACKOFF(3),
  MAX_PEERS(4),
  MAX_TRIES(5),
  UNAVAILABLE(6),
  BUSY(7);

  private final short key;

  ErrorCode(short key) {
    this.key = key;
  }

  ErrorCode(int key) {
    this.key = (short) key;
  }

  public short getKey() {
    return key;
  }
}
