package io.github.hapjava.server.impl.crypto;

import java.io.IOException;
import org.bouncycastle.crypto.engines.ChaChaEngine;
import org.bouncycastle.crypto.generators.Poly1305KeyGenerator;
import org.bouncycastle.crypto.params.KeyParameter;
import org.bouncycastle.crypto.params.ParametersWithIV;
import org.bouncycastle.crypto.tls.AlertDescription;
import org.bouncycastle.crypto.tls.TlsFatalAlert;
import org.bouncycastle.util.Arrays;

public class ChachaDecoder {

  private final ChaChaEngine decryptCipher;

  public ChachaDecoder(byte[] key, byte[] nonce) throws IOException {

    this.decryptCipher = new ChaChaEngine(20);

    this.decryptCipher.init(false, new ParametersWithIV(new KeyParameter(key), nonce));
  }

  public byte[] decodeCiphertext(byte[] receivedMAC, byte[] additionalData, byte[] ciphertext)
      throws IOException {

    KeyParameter macKey = initRecordMAC(decryptCipher);

    byte[] calculatedMAC = PolyKeyCreator.create(macKey, additionalData, ciphertext);

    if (!Arrays.constantTimeAreEqual(calculatedMAC, receivedMAC)) {
      throw new TlsFatalAlert(AlertDescription.bad_record_mac);
    }

    byte[] output = new byte[ciphertext.length];
    decryptCipher.processBytes(ciphertext, 0, ciphertext.length, output, 0);

    return output;
  }

  public byte[] decodeCiphertext(byte[] receivedMAC, byte[] ciphertext) throws IOException {
    return decodeCiphertext(receivedMAC, null, ciphertext);
  }

  private KeyParameter initRecordMAC(ChaChaEngine cipher) {
    byte[] firstBlock = new byte[64];
    cipher.processBytes(firstBlock, 0, firstBlock.length, firstBlock, 0);

    // NOTE: The older BC implementation puts 'r' after 'k'
    KeyParameter macKey = new KeyParameter(firstBlock, 0, 32);
    Poly1305KeyGenerator.clamp(macKey.getKey());
    return macKey;
  }
}
