package io.github.hapjava.impl.pairing;

import java.nio.ByteBuffer;

public class UpgradeResponse extends PairingResponse {

  private final byte[] readKey;
  private final byte[] writeKey;

  UpgradeResponse(byte[] body, byte[] readKey, byte[] writeKey) {
    super(body);
    this.readKey = readKey;
    this.writeKey = writeKey;
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
}
