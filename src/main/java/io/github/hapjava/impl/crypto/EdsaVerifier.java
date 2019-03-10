package io.github.hapjava.impl.crypto;

import java.security.MessageDigest;
import java.security.PublicKey;
import java.security.Signature;
import net.i2p.crypto.eddsa.EdDSAEngine;
import net.i2p.crypto.eddsa.EdDSAPublicKey;
import net.i2p.crypto.eddsa.spec.EdDSANamedCurveTable;
import net.i2p.crypto.eddsa.spec.EdDSAParameterSpec;
import net.i2p.crypto.eddsa.spec.EdDSAPublicKeySpec;

public class EdsaVerifier {

  private final PublicKey publicKey;

  public EdsaVerifier(byte[] publicKey) {
    EdDSAParameterSpec spec = EdDSANamedCurveTable.getByName("ed25519-sha-512");
    EdDSAPublicKeySpec pubKey = new EdDSAPublicKeySpec(publicKey, spec);
    this.publicKey = new EdDSAPublicKey(pubKey);
  }

  public boolean verify(byte[] data, byte[] signature) throws Exception {
    Signature sgr = new EdDSAEngine(MessageDigest.getInstance("SHA-512"));
    sgr.initVerify(publicKey);
    sgr.update(data);

    return sgr.verify(signature);
  }
}
