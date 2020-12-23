package io.github.hapjava.server.impl.pairing;

import static io.github.hapjava.server.impl.pairing.ByteUtils.toUnsignedByteArray;

import com.nimbusds.srp6.*;
import io.github.hapjava.server.impl.http.HttpResponse;
import io.github.hapjava.server.impl.pairing.HomekitSRP6ServerSession.State;
import io.github.hapjava.server.impl.pairing.PairSetupRequest.Stage2Request;
import io.github.hapjava.server.impl.pairing.TypeLengthValueUtils.Encoder;
import io.github.hapjava.server.impl.responses.ConflictResponse;
import java.math.BigInteger;
import java.security.MessageDigest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class SrpHandler {

  private static final int BIT_SIZE = 3072;
  private static final String HASH_ALG_H = "SHA-512";
  private static final String IDENTIFIER = "Pair-Setup";

  private static final Logger logger = LoggerFactory.getLogger(SrpHandler.class);

  private final BigInteger salt;
  private final HomekitSRP6ServerSession session;
  private final SRP6CryptoParams config;
  private final String pin;

  public SrpHandler(String pin, BigInteger salt) {
    config = SRP6CryptoParams.getInstance(BIT_SIZE, HASH_ALG_H);
    session = new HomekitSRP6ServerSession(config);
    session.setClientEvidenceRoutine(new ClientEvidenceRoutineImpl());
    session.setServerEvidenceRoutine(new ServerEvidenceRoutineImpl());
    this.pin = pin;
    this.salt = salt;
  }

  HttpResponse step1() throws Exception {
    if (session.getState() != State.INIT) {
      logger.warn("Session is not in state INIT when receiving step1");
      return new ConflictResponse();
    }

    SRP6VerifierGenerator verifierGenerator = new SRP6VerifierGenerator(config);
    verifierGenerator.setXRoutine(new XRoutineWithUserIdentity());
    BigInteger verifier = verifierGenerator.generateVerifier(salt, IDENTIFIER, pin);

    Encoder encoder = TypeLengthValueUtils.getEncoder();
    encoder.add(MessageType.STATE, (short) 0x02);
    encoder.add(MessageType.SALT, salt);
    encoder.add(MessageType.PUBLIC_KEY, session.step1(IDENTIFIER, salt, verifier));
    return new PairingResponse(encoder.toByteArray());
  }

  HttpResponse step2(Stage2Request request) throws Exception {
    if (session.getState() != State.STEP_1) {
      logger.warn("Session is not in state Stage 1 when receiving step2");
      return new ConflictResponse();
    }
    BigInteger m2 = session.step2(request.getA(), request.getM1());
    Encoder encoder = TypeLengthValueUtils.getEncoder();
    encoder.add(MessageType.STATE, (short) 0x04);
    encoder.add(MessageType.PROOF, m2);
    return new PairingResponse(encoder.toByteArray());
  }

  byte[] getK() {
    MessageDigest digest = session.getCryptoParams().getMessageDigestInstance();
    BigInteger S = session.getSessionKey();
    byte[] sBytes = toUnsignedByteArray(S);
    return digest.digest(sBytes);
  }
}
