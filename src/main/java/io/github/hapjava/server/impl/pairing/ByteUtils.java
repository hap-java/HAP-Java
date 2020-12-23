package io.github.hapjava.server.impl.pairing;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigInteger;
import java.util.Arrays;

class ByteUtils {

  public static byte[] joinBytes(byte[]... piece) {
    int pos = 0;
    int length = 0;
    for (int i = 0; i < piece.length; i++) {
      length += piece[i].length;
    }
    byte[] ret = new byte[length];
    for (int i = 0; i < piece.length; i++) {
      System.arraycopy(piece[i], 0, ret, pos, piece[i].length);
      pos += piece[i].length;
    }
    return ret;
  }

  public static byte[] toUnsignedByteArray(BigInteger i) {
    byte[] array = i.toByteArray();
    if (array[0] == 0) {
      array = Arrays.copyOfRange(array, 1, array.length);
    }
    return array;
  }

  public static void copyStream(InputStream input, OutputStream output, int length)
      throws IOException {
    byte[] buffer = new byte[length];
    int remaining = length;
    int bytesRead;
    while ((bytesRead = input.read(buffer, 0, remaining)) != -1 && remaining > 0) {
      output.write(buffer, 0, bytesRead);
      remaining -= bytesRead;
    }
  }
}
