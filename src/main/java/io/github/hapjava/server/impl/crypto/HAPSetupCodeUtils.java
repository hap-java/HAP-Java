package io.github.hapjava.server.impl.crypto;

import java.util.Base64;
import org.bouncycastle.crypto.Digest;
import org.bouncycastle.crypto.digests.SHA512Digest;

public class HAPSetupCodeUtils {

  private static byte[] calculateHash(final String input, final Digest digest) {
    byte[] inputAsBytes = input.getBytes();
    byte[] retValue = new byte[digest.getDigestSize()];
    digest.update(inputAsBytes, 0, inputAsBytes.length);
    digest.doFinal(retValue, 0);
    return retValue;
  }

  /**
   * generate SHA52 Hash for given string. The hash is used for mDNS advertisement.
   *
   * @param value value
   * @return hash
   */
  public static String generateSHA512Hash(final String value) {
    final byte[] hash = calculateHash(value.toUpperCase(), new SHA512Digest());
    final byte[] hashTuncate = new byte[4];
    System.arraycopy(hash, 0, hashTuncate, 0, 4);
    String hashStr = Base64.getEncoder().encodeToString(hashTuncate);
    return hashStr;
  }

  /**
   * generate Setup URI which can be used fo QR Code generation.
   *
   * @param pin PIN number without "-"
   * @param setupId alphanumeric string of the length 4
   * @param category accessory category
   * @return setup UID
   */
  public static String getSetupURI(final String pin, final String setupId, final int category) {
    long code =
        0 << 43 // Version
            | 0 << 39 // Reserved
            | ((long) category) << 31 // Category
            | 0 << 29 // BLE support
            | 1 << 28 // IP support
            | 0 << 27 // Paired  / NFC
            | Integer.valueOf(pin); // PIN
    String payload = Long.toString(code, 36) + setupId;
    while (payload.length() < 13) {
      payload = '0' + payload;
    }
    return "X-HM://" + payload.toUpperCase();
  }
}
