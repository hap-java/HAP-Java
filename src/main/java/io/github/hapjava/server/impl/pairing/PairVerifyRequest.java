package io.github.hapjava.server.impl.pairing;

import io.github.hapjava.server.impl.pairing.TypeLengthValueUtils.DecodeResult;

abstract class PairVerifyRequest {

  static PairVerifyRequest of(byte[] content) throws Exception {
    DecodeResult d = TypeLengthValueUtils.decode(content);
    short stage = d.getByte(MessageType.STATE);
    switch (stage) {
      case 1:
        return new VerifyStartRequest(d);

      case 3:
        return new VerifyFinishRequest(d);

      default:
        throw new Exception("Unknown pair process stage: " + stage);
    }
  }

  // Raw integer.
  // State of the pairing process. 1=M1, 2=M2, etc.
  abstract int getState();

  static class VerifyStartRequest extends PairVerifyRequest {

    private final byte[] clientPublicKey;

    public VerifyStartRequest(DecodeResult d) {
      clientPublicKey = d.getBytes(MessageType.PUBLIC_KEY);
    }

    public byte[] getClientPublicKey() {
      return clientPublicKey;
    }

    @Override
    int getState() {
      return 1;
    }
  }

  static class VerifyFinishRequest extends PairVerifyRequest {

    private final byte[] messageData;
    private final byte[] authTagData;

    public VerifyFinishRequest(DecodeResult d) {
      messageData = new byte[d.getLength(MessageType.ENCRYPTED_DATA) - 16];
      authTagData = new byte[16];
      d.getBytes(MessageType.ENCRYPTED_DATA, messageData, 0);
      d.getBytes(MessageType.ENCRYPTED_DATA, authTagData, messageData.length);
    }

    public byte[] getMessageData() {
      return messageData;
    }

    public byte[] getAuthTagData() {
      return authTagData;
    }

    @Override
    public int getState() {
      return 3;
    }
  }
}
