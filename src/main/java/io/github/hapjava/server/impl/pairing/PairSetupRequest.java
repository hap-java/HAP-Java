package io.github.hapjava.server.impl.pairing;

import io.github.hapjava.server.impl.pairing.TypeLengthValueUtils.DecodeResult;
import java.math.BigInteger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

abstract class PairSetupRequest {
  private static final Logger logger = LoggerFactory.getLogger(PairSetupRequest.class);

  public static PairSetupRequest of(byte[] content) throws Exception {
    DecodeResult d = TypeLengthValueUtils.decode(content);
    logger.trace("Decoded pair setup request: {}", d);
    short stage = d.getByte(MessageType.STATE);
    switch (stage) {
      case 1:
        return new SRPStartRequest(d);

      case 3:
        return new SRPVerifyRequest(d);

      case 5:
        return new ExchangeRequest(d);

      default:
        throw new Exception("Unknown pair process stage: " + stage);
    }
  }

  // Raw integer.
  // State of the pairing process. 1=M1, 2=M2, etc.
  public abstract int getState();

  public static class SRPStartRequest extends PairSetupRequest {
    int flags;

    public SRPStartRequest(DecodeResult d) {
      flags = 0;
      if (d.hasMessage(MessageType.FLAGS)) {
        flags = d.getInt(MessageType.FLAGS);
      }
    }

    @Override
    public int getState() {
      return 1;
    }

    public String toString() {
      return "<M1 flags=" + Integer.toString(flags) + ">";
    }
  }

  public static class SRPVerifyRequest extends PairSetupRequest {

    private final BigInteger a;
    private final BigInteger m1;

    public SRPVerifyRequest(DecodeResult d) {
      a = d.getBigInt(MessageType.PUBLIC_KEY);
      m1 = d.getBigInt(MessageType.PROOF);
    }

    public BigInteger getA() {
      return a;
    }

    public BigInteger getM1() {
      return m1;
    }

    @Override
    public int getState() {
      return 3;
    }

    public String toString() {
      return "<M3 public key=" + a.toString() + ", proof=" + m1.toString() + ">";
    }
  }

  static class ExchangeRequest extends PairSetupRequest {

    private final byte[] messageData;
    private final byte[] authTagData;

    public ExchangeRequest(DecodeResult d) {
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
      return 5;
    }

    public String toString() {
      return "<M5>";
    }
  }
}
