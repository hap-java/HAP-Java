package io.github.hapjava.server.impl.crypto;

import java.io.IOException;
import org.bouncycastle.crypto.InvalidCipherTextException;
import org.bouncycastle.crypto.modes.ChaCha20Poly1305;
import org.bouncycastle.crypto.params.AEADParameters;
import org.bouncycastle.crypto.params.KeyParameter;

public class ChachaEncoder {

  private final ChaCha20Poly1305 cipher;
  private final byte[] key;
  private final byte[] nonce;

  public ChachaEncoder(byte[] key, byte[] nonce) throws IOException {
    this.key = key;
    // ChaCha20-Poly1305 requires exactly 12 bytes (96 bits) for nonce
    this.nonce = ensureNonceSize(nonce);
    this.cipher = new ChaCha20Poly1305();
  }

  private byte[] ensureNonceSize(byte[] nonce) {
    if (nonce == null) {
      return new byte[12]; // Default to zero nonce if null
    }

    // For HomeKit pairing messages, handle Apple's string-based nonces
    if (nonce.length == 8) {
      // Apple's HomeKit implementation uses a specific nonce format
      // Based on RFC 7539 and Apple's implementation, the nonce should be:
      // - 4 bytes constant (0x00000000)
      // - 8 bytes nonce string
      // This matches ChaCha20's 96-bit nonce requirement and ChachaDecoder format
      byte[] adjustedNonce = new byte[12];
      // Put the 8-byte nonce at the END (bytes 4-11), not at the beginning
      System.arraycopy(nonce, 0, adjustedNonce, 4, 8);
      // First 4 bytes remain zero (counter initialization)
      return adjustedNonce;
    }

    if (nonce.length == 12) {
      return nonce; // Already correct size
    }

    byte[] adjustedNonce = new byte[12];
    if (nonce.length < 12) {
      // Pad with zeros if too short
      System.arraycopy(nonce, 0, adjustedNonce, 0, nonce.length);
    } else {
      // Truncate if too long
      System.arraycopy(nonce, 0, adjustedNonce, 0, 12);
    }
    return adjustedNonce;
  }

  private static String bytesToHex(byte[] bytes) {
    StringBuilder result = new StringBuilder();
    for (byte b : bytes) {
      result.append(String.format("%02x", b));
    }
    return result.toString();
  }

  public byte[] encodeCiphertext(byte[] plaintext) throws IOException {
    return encodeCiphertext(plaintext, null);
  }

  public byte[] encodeCiphertext(byte[] plaintext, byte[] additionalData) throws IOException {
    try {
      // Use the nonce provided during construction
      AEADParameters params = new AEADParameters(new KeyParameter(key), 128, nonce, additionalData);
      cipher.init(true, params);

      byte[] output = new byte[cipher.getOutputSize(plaintext.length)];
      int len = cipher.processBytes(plaintext, 0, plaintext.length, output, 0);
      len += cipher.doFinal(output, len);

      // Split the result into ciphertext and MAC
      byte[] ciphertext = new byte[plaintext.length];
      byte[] mac = new byte[16];
      System.arraycopy(output, 0, ciphertext, 0, plaintext.length);
      System.arraycopy(output, plaintext.length, mac, 0, 16);

      // Return combined ciphertext + MAC as expected by the original interface
      byte[] ret = new byte[ciphertext.length + 16];
      System.arraycopy(ciphertext, 0, ret, 0, ciphertext.length);
      System.arraycopy(mac, 0, ret, ciphertext.length, 16);
      return ret;
    } catch (InvalidCipherTextException e) {
      throw new IOException("Encryption failed", e);
    }
  }
}
