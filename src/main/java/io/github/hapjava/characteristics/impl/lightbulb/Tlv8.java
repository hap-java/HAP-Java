package io.github.hapjava.characteristics.impl.lightbulb;

import java.io.ByteArrayOutputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/** TLV8 as used by characteristic values. */
final class Tlv8 {

  private Tlv8() {}

  /**
   * @return the values per type; a type has several values when it is a list. Values longer than
   *     255 bytes, split into several items, are joined.
   */
  static Map<Integer, List<byte[]>> decode(byte[] data) {
    Map<Integer, List<byte[]>> result = new HashMap<>();
    int lastType = -1;
    int lastLength = -1;
    int i = 0;
    while (i + 2 <= data.length) {
      int type = data[i] & 0xFF;
      int length = data[i + 1] & 0xFF;
      if (i + 2 + length > data.length) {
        throw new IllegalArgumentException("TLV8 item exceeds data");
      }
      byte[] value = new byte[length];
      System.arraycopy(data, i + 2, value, 0, length);
      i += 2 + length;

      List<byte[]> values = result.computeIfAbsent(type, t -> new ArrayList<>());
      if (type == lastType && lastLength == 255) {
        byte[] previous = values.get(values.size() - 1);
        byte[] joined = new byte[previous.length + length];
        System.arraycopy(previous, 0, joined, 0, previous.length);
        System.arraycopy(value, 0, joined, previous.length, length);
        values.set(values.size() - 1, joined);
      } else {
        values.add(value);
      }
      lastType = type;
      lastLength = length;
    }
    return result;
  }

  /** @return the first value of the type, or null if there is none. */
  static byte[] get(Map<Integer, List<byte[]>> tlv, int type) {
    List<byte[]> values = tlv.get(type);
    return values == null ? null : values.get(0);
  }

  static void write(ByteArrayOutputStream out, int type, byte[] value) {
    int offset = 0;
    do {
      int length = Math.min(255, value.length - offset);
      out.write(type);
      out.write(length);
      out.write(value, offset, length);
      offset += length;
    } while (offset < value.length);
  }

  /** @return the value as little endian unsigned integer of 1, 2, 4 or 8 bytes. */
  static byte[] uint(long value) {
    int length = value <= 0xFF ? 1 : value <= 0xFFFF ? 2 : value <= 0xFFFFFFFFL ? 4 : 8;
    byte[] bytes = new byte[length];
    for (int i = 0; i < length; i++) {
      bytes[i] = (byte) (value >>> (8 * i));
    }
    return bytes;
  }

  static long readUint(byte[] bytes) {
    long value = 0;
    for (int i = bytes.length - 1; i >= 0; i--) {
      value = (value << 8) | (bytes[i] & 0xFF);
    }
    return value;
  }

  static float readFloat(byte[] bytes) {
    return ByteBuffer.wrap(bytes).order(ByteOrder.LITTLE_ENDIAN).getFloat();
  }
}
