package io.github.hapjava.server.impl.crypto;

import java.io.IOException;
import org.bouncycastle.crypto.InvalidCipherTextException;
import org.bouncycastle.crypto.modes.ChaCha20Poly1305;
import org.bouncycastle.crypto.params.AEADParameters;
import org.bouncycastle.crypto.params.KeyParameter;
import org.bouncycastle.tls.AlertDescription;
import org.bouncycastle.tls.TlsFatalAlert;

public class ChachaDecoder {

  private final ChaCha20Poly1305 cipher;
  private final byte[] key;
  private final byte[] nonce;

  public ChachaDecoder(byte[] key, byte[] nonce) throws IOException {
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
      // This matches ChaCha20's 96-bit nonce requirement
      byte[] adjustedNonce = new byte[12];
      // Put the 8-byte nonce at the END (bytes 4-11), not at the beginning
      System.arraycopy(nonce, 0, adjustedNonce, 4, 8);
      // First 4 bytes remain zero (counter initialization)
      return adjustedNonce;
    }

    if (nonce.length == 12) {
      return nonce; // Already correct size
    }

    // For other nonce lengths, pad or truncate to 12 bytes
    byte[] adjustedNonce = new byte[12];
    if (nonce.length < 12) {
      // Pad with zeros if too short - put nonce at beginning
      System.arraycopy(nonce, 0, adjustedNonce, 0, nonce.length);
      // Remaining bytes are already zero
    } else {
      // Truncate if too long - take first 12 bytes
      System.arraycopy(nonce, 0, adjustedNonce, 0, 12);
    }

    return adjustedNonce;
  }

  public byte[] decodeCiphertext(byte[] receivedMAC, byte[] additionalData, byte[] ciphertext)
      throws IOException {

    try {
      byte[] ciphertextWithTag = new byte[ciphertext.length + receivedMAC.length];
      System.arraycopy(ciphertext, 0, ciphertextWithTag, 0, ciphertext.length);
      System.arraycopy(receivedMAC, 0, ciphertextWithTag, ciphertext.length, receivedMAC.length);

      ChaCha20Poly1305 cipher1 = new ChaCha20Poly1305();
      AEADParameters params = new AEADParameters(new KeyParameter(key), 128, nonce, additionalData);
      cipher1.init(false, params);

      byte[] output = new byte[cipher1.getOutputSize(ciphertextWithTag.length)];
      int len = cipher1.processBytes(ciphertextWithTag, 0, ciphertextWithTag.length, output, 0);
      len += cipher1.doFinal(output, len);

      byte[] result = new byte[len];
      System.arraycopy(output, 0, result, 0, len);

      return result;

    } catch (InvalidCipherTextException e) {
      throw new TlsFatalAlert(AlertDescription.bad_record_mac);
    }
  }

  private static String bytesToHex(byte[] bytes) {
    StringBuilder result = new StringBuilder();
    for (byte b : bytes) {
      result.append(String.format("%02x", b));
    }
    return result.toString();
  }

  public byte[] decodeCiphertext(byte[] receivedMAC, byte[] ciphertext) throws IOException {
    return decodeCiphertext(receivedMAC, null, ciphertext);
  }
}
