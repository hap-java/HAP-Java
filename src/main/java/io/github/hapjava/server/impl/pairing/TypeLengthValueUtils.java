package io.github.hapjava.server.impl.pairing;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TypeLengthValueUtils {

  private TypeLengthValueUtils() {}

  public static DecodeResult decode(byte[] content) throws IOException {
    DecodeResult ret = new DecodeResult();
    ByteArrayInputStream bais = new ByteArrayInputStream(content);
    while (bais.available() > 0) {
      short type = (byte) (bais.read() & 0xFF);
      int length = bais.read();
      byte[] part = new byte[length];
      bais.read(part);
      ret.add(type, part);
    }
    return ret;
  }

  public static Encoder getEncoder() {
    return new Encoder();
  }

  private static void assertByte(short b) {
    if (b > 255 || b < 0) throw new AssertionError("invalid byte: " + b);
  }

  public static final class Encoder {

    private final ByteArrayOutputStream baos;

    private Encoder() {
      baos = new ByteArrayOutputStream();
    }

    public Encoder add(short type, BigInteger i) {
      return add(type, ByteUtils.toByteArray(i));
    }

    public Encoder add(short type, short b) {
      assertByte(type);
      assertByte(b);
      baos.write(type);
      baos.write(1);
      baos.write(b);
      return this;
    }

    public Encoder addShort(short type, int b) {
      if (b >= 1 << 16) throw new AssertionError("invalid MessageType short value: " + b);
      ByteBuffer byteBuffer = ByteBuffer.allocate(2).order(ByteOrder.LITTLE_ENDIAN);
      byteBuffer.putShort((short) b);
      return add(type, byteBuffer.array());
    }

    public Encoder addInt(short type, int b) {
      ByteBuffer byteBuffer = ByteBuffer.allocate(4).order(ByteOrder.LITTLE_ENDIAN);
      byteBuffer.putInt(b);
      return add(type, byteBuffer.array());
    }

    public Encoder add(short type, byte[] bytes) {
      assertByte(type);

      int offset = 0;
      while (offset < bytes.length) {
        int toWrite = bytes.length - offset;
        toWrite = Math.min(toWrite, 255);
        baos.write(type);
        baos.write(toWrite);
        baos.write(bytes, offset, toWrite);
        offset += toWrite;
      }

      return this;
    }

    public Encoder add(MessageType type, BigInteger i) {
      return add(type.getKey(), i);
    }

    public Encoder add(MessageType type, short b) {
      return add(type.getKey(), b);
    }

    public Encoder add(MessageType type, byte[] bytes) {
      return add(type.getKey(), bytes);
    }

    public <T extends TLVObject> Encoder addAllEmptySeparated(short type, List<T> objects) {
      int i = 0;
      for (T object : objects) {
        if (i > 0) add((short) 0, new byte[0]);
        add(type, object.encodeTLV());
        i++;
      }
      return this;
    }

    public byte[] toByteArray() {
      return baos.toByteArray();
    }
  }

  public static final class DecodeResult {
    private final Map<Short, byte[]> result = new HashMap<>();

    private DecodeResult() {}

    public byte getByte(short type) {
      assertByte(type);
      return result.get(type)[0];
    }

    public int getShort(short type) {
      assertByte(type);
      byte[] bytes = result.get(type);
      if (bytes.length != 2) throw new AssertionError("homekit expected short");
      return ByteBuffer.wrap(bytes).order(ByteOrder.LITTLE_ENDIAN).getShort() & 0x0000FFFF;
    }

    public BigInteger getBigInt(short type) {
      assertByte(type);
      return new BigInteger(1, result.get(type));
    }

    public byte[] getBytes(short type) {
      assertByte(type);
      return result.get(type);
    }

    public void getBytes(short type, byte[] dest, int srcOffset) {
      assertByte(type);
      byte[] b = result.get(type);
      System.arraycopy(b, srcOffset, dest, 0, Math.min(dest.length, b.length));
    }

    public int getLength(short type) {
      assertByte(type);
      return result.get(type).length;
    }

    public String getString(short type) {
      assertByte(type);
      return new String(getBytes(type));
    }

    public byte getByte(MessageType type) {
      return getByte(type.getKey());
    }

    public BigInteger getBigInt(MessageType type) {
      return getBigInt(type.getKey());
    }

    public byte[] getBytes(MessageType type) {
      return getBytes(type.getKey());
    }

    public void getBytes(MessageType type, byte[] dest, int srcOffset) {
      getBytes(type.getKey(), dest, srcOffset);
    }

    public int getLength(MessageType type) {
      return getLength(type.getKey());
    }

    private void add(short type, byte[] bytes) {
      result.merge(type, bytes, ByteUtils::joinBytes);
    }
  }
}
