package io.github.hapjava.server.impl.pairing;

import java.nio.ByteBuffer;

public class UpgradeResponse extends PairingResponse {

  private final byte[] readKey;
  private final byte[] writeKey;
  private final String username;

  UpgradeResponse(byte[] body, byte[] readKey, byte[] writeKey, String username) {
    super(body);
    this.readKey = readKey;
    this.writeKey = writeKey;
    this.username = username;
  }

  @Override
  public boolean doUpgrade() {
    return true;
  }

  public ByteBuffer getReadKey() {
    return ByteBuffer.wrap(readKey);
  }

  public ByteBuffer getWriteKey() {
    return ByteBuffer.wrap(writeKey);
  }

  public String getUsername() {
    return username;
  }
}
