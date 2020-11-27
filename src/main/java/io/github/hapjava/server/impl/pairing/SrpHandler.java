package io.github.hapjava.server.impl.pairing;

import com.nimbusds.srp6.*;
import io.github.hapjava.server.impl.http.HttpResponse;
import io.github.hapjava.server.impl.pairing.HomekitSRP6ServerSession.State;
import io.github.hapjava.server.impl.pairing.PairSetupRequest.Stage2Request;
import io.github.hapjava.server.impl.pairing.TypeLengthValueUtils.Encoder;
import io.github.hapjava.server.impl.responses.ConflictResponse;
import io.github.hapjava.server.impl.responses.NotFoundResponse;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.Arrays;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class SrpHandler {

  //	Precomputed safe 3072 bit prime 'N'. Origin RFC 5054, appendix A.
  private static final BigInteger N_3072 =
      new BigInteger(
          "5809605995369958062791915965639201402176612226902900533702900882779736177890990861472094774477339581147373410185646378328043729800750470098210924487866935059164371588168047540943981644516632755067501626434556398193186628990071248660819361205119793693985433297036118232914410171876807536457391277857011849897410207519105333355801121109356897459426271845471397952675959440793493071628394122780510124618488232602464649876850458861245784240929258426287699705312584509625419513463605155428017165714465363094021609290561084025893662561222573202082865797821865270991145082200656978177192827024538990239969175546190770645685893438011714430426409338676314743571154537142031573004276428701433036381801705308659830751190352946025482059931306571004727362479688415574702596946457770284148435989129632853918392117997472632693078113129886487399347796982772784615865232621289656944284216824611318709764535152507354116344703769998514148343807");
  private static final BigInteger G = BigInteger.valueOf(5);
  private static final String IDENTIFIER = "Pair-Setup";

  private static final Logger logger = LoggerFactory.getLogger(SrpHandler.class);

  private final BigInteger salt;
  private final HomekitSRP6ServerSession session;
  private final SRP6CryptoParams config;
  private final String pin;

  public SrpHandler(String pin, BigInteger salt) {
    config = new SRP6CryptoParams(N_3072, G, "SHA-512");
    session = new HomekitSRP6ServerSession(config);
    session.setClientEvidenceRoutine(new ClientEvidenceRoutineImpl());
    session.setServerEvidenceRoutine(new ServerEvidenceRoutineImpl());
    this.pin = pin;
    this.salt = salt;
  }

  public HttpResponse handle(PairSetupRequest request) throws Exception {
    switch (request.getStage()) {
      case ONE:
        return step1();

      case TWO:
        return step2((Stage2Request) request);

      default:
        return new NotFoundResponse();
    }
  }

  private HttpResponse step1() throws Exception {
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

  private HttpResponse step2(Stage2Request request) throws Exception {
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

  public byte[] getK() {
    MessageDigest digest = session.getCryptoParams().getMessageDigestInstance();
    BigInteger S = session.getSessionKey();
    byte[] sBytes = bigIntegerToUnsignedByteArray(S);
    return digest.digest(sBytes);
  }

  public static byte[] bigIntegerToUnsignedByteArray(BigInteger i) {
    byte[] array = i.toByteArray();
    if (array[0] == 0) {
      array = Arrays.copyOfRange(array, 1, array.length);
    }
    return array;
  }
}
