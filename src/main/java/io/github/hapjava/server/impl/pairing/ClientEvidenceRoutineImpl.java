package io.github.hapjava.server.impl.pairing;

import static io.github.hapjava.server.impl.pairing.ByteUtils.toUnsignedByteArray;

import com.nimbusds.srp6.*;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

class ClientEvidenceRoutineImpl implements ClientEvidenceRoutine {

  public ClientEvidenceRoutineImpl() {}

  /**
   * Calculates M1 according to the following formula:
   *
   * <p>M1 = H(H(N) xor H(g) || H(username) || s || A || B || H(S))
   */
  @Override
  public BigInteger computeClientEvidence(
      SRP6CryptoParams cryptoParams, SRP6ClientEvidenceContext ctx) {

    MessageDigest digest;
    try {
      digest = MessageDigest.getInstance(cryptoParams.H);
    } catch (NoSuchAlgorithmException e) {
      throw new RuntimeException("Could not locate requested algorithm", e);
    }
    digest.update(toUnsignedByteArray(cryptoParams.N));
    byte[] hN = digest.digest();

    digest.update(toUnsignedByteArray(cryptoParams.g));
    byte[] hg = digest.digest();

    byte[] hNhg = xor(hN, hg);

    digest.update(ctx.userID.getBytes(StandardCharsets.UTF_8));
    byte[] hu = digest.digest();

    digest.update(toUnsignedByteArray(ctx.S));
    byte[] hS = digest.digest();

    digest.update(hNhg);
    digest.update(hu);
    digest.update(toUnsignedByteArray(ctx.s));
    digest.update(toUnsignedByteArray(ctx.A));
    digest.update(toUnsignedByteArray(ctx.B));
    digest.update(hS);
    BigInteger ret = new BigInteger(1, digest.digest());
    return ret;
  }

  private static byte[] xor(byte[] b1, byte[] b2) {
    byte[] result = new byte[b1.length];
    for (int i = 0; i < b1.length; i++) {
      result[i] = (byte) (b1[i] ^ b2[i]);
    }
    return result;
  }
}
