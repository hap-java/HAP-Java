package io.github.hapjava.server.impl.crypto;

import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.Signature;
import java.security.SignatureException;
import net.i2p.crypto.eddsa.EdDSAEngine;
import net.i2p.crypto.eddsa.EdDSAPrivateKey;
import net.i2p.crypto.eddsa.EdDSAPublicKey;
import net.i2p.crypto.eddsa.spec.EdDSANamedCurveTable;
import net.i2p.crypto.eddsa.spec.EdDSAParameterSpec;
import net.i2p.crypto.eddsa.spec.EdDSAPrivateKeySpec;
import net.i2p.crypto.eddsa.spec.EdDSAPublicKeySpec;

public class EdsaSigner {

  private final EdDSAPublicKey publicKey;
  private final EdDSAPrivateKey privateKey;

  public EdsaSigner(byte[] privateKeyBytes) {
    EdDSAParameterSpec spec = EdDSANamedCurveTable.getByName("ed25519-sha-512");
    EdDSAPrivateKeySpec privateKeySpec = new EdDSAPrivateKeySpec(privateKeyBytes, spec);
    EdDSAPublicKeySpec pubKeySpec = new EdDSAPublicKeySpec(privateKeySpec.getA(), spec);
    publicKey = new EdDSAPublicKey(pubKeySpec);
    privateKey = new EdDSAPrivateKey(privateKeySpec);
  }

  public byte[] getPublicKey() {
    return publicKey.getAbyte();
  }

  public byte[] sign(byte[] material)
      throws NoSuchAlgorithmException, InvalidKeyException, SignatureException {
    Signature sgr = new EdDSAEngine(MessageDigest.getInstance("SHA-512"));
    sgr.initSign(privateKey);
    sgr.update(material);
    return sgr.sign();
  }
}
