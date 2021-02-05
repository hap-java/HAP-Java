package io.github.hapjava.server.impl.pairing;

import com.nimbusds.srp6.BigIntegerUtils;
import com.nimbusds.srp6.SRP6CryptoParams;
import com.nimbusds.srp6.SRP6ServerEvidenceContext;
import com.nimbusds.srp6.ServerEvidenceRoutine;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

class ServerEvidenceRoutineImpl implements ServerEvidenceRoutine {

  @Override
  public BigInteger computeServerEvidence(
      SRP6CryptoParams cryptoParams, SRP6ServerEvidenceContext ctx) {

    MessageDigest digest;
    try {
      digest = MessageDigest.getInstance(cryptoParams.H);
    } catch (NoSuchAlgorithmException e) {
      throw new RuntimeException("Could not locate requested algorithm", e);
    }

    byte[] hS = digest.digest(BigIntegerUtils.bigIntegerToBytes(ctx.S));

    digest.update(BigIntegerUtils.bigIntegerToBytes(ctx.A));
    digest.update(BigIntegerUtils.bigIntegerToBytes(ctx.M1));
    digest.update(hS);

    return new BigInteger(1, digest.digest());
  }
}
