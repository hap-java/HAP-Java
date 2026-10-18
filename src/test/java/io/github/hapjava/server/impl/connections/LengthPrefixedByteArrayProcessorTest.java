package io.github.hapjava.server.impl.connections;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import org.junit.Test;

public class LengthPrefixedByteArrayProcessorTest {

  private static final int TAG_LENGTH = 16;

  /**
   * Builds a frame as it arrives on the wire: 2-byte little-endian payload length, payload, 16-byte
   * tag. The payload and tag bytes start at 0x80 so that they never look like a short length.
   */
  private static byte[] frame(int payloadLength, int seed) {
    byte[] frame = new byte[2 + payloadLength + TAG_LENGTH];
    frame[0] = (byte) (payloadLength & 0xFF);
    frame[1] = (byte) ((payloadLength >> 8) & 0xFF);
    for (int i = 2; i < frame.length; i++) {
      frame[i] = (byte) (0x80 + seed + i);
    }
    return frame;
  }

  /** What the processor is expected to emit for a frame: payload + tag, without the length. */
  private static byte[] body(byte[] frame) {
    return Arrays.copyOfRange(frame, 2, frame.length);
  }

  private static byte[] concat(byte[]... parts) {
    ByteArrayOutputStream out = new ByteArrayOutputStream();
    for (byte[] part : parts) {
      out.write(part, 0, part.length);
    }
    return out.toByteArray();
  }

  private static List<byte[]> feed(LengthPrefixedByteArrayProcessor processor, byte[]... reads) {
    List<byte[]> results = new ArrayList<>();
    for (byte[] read : reads) {
      results.addAll(processor.handle(read));
    }
    return results;
  }

  private static List<Integer> sizes(Collection<byte[]> results) {
    List<Integer> sizes = new ArrayList<>();
    for (byte[] result : results) {
      sizes.add(result.length);
    }
    return sizes;
  }

  private static void assertBodies(List<byte[]> results, byte[]... frames) {
    List<Integer> expectedSizes = new ArrayList<>();
    for (byte[] frame : frames) {
      expectedSizes.add(frame.length - 2);
    }
    assertThat(sizes(results)).isEqualTo(expectedSizes);
    for (int i = 0; i < frames.length; i++) {
      assertThat(results.get(i)).as("frame %d", i).isEqualTo(body(frames[i]));
    }
  }

  @Test
  public void singleFrameInOneRead() {
    byte[] a = frame(100, 1);
    assertBodies(feed(new LengthPrefixedByteArrayProcessor(), a), a);
  }

  @Test
  public void threeFramesInOneRead() {
    byte[] a = frame(1024, 1);
    byte[] b = frame(1024, 2);
    byte[] c = frame(200, 3);
    assertBodies(feed(new LengthPrefixedByteArrayProcessor(), concat(a, b, c)), a, b, c);
  }

  @Test
  public void frameSplitAcrossTwoReads() {
    byte[] a = frame(1024, 1);
    int[] offsets = {1, 2, 3, 10, 18, 19, 20, 500, a.length - 1};
    for (int offset : offsets) {
      byte[] first = Arrays.copyOfRange(a, 0, offset);
      byte[] second = Arrays.copyOfRange(a, offset, a.length);
      List<byte[]> results = feed(new LengthPrefixedByteArrayProcessor(), first, second);
      assertThat(sizes(results)).as("split at %d", offset).containsExactly(a.length - 2);
      assertThat(results.get(0)).as("split at %d", offset).isEqualTo(body(a));
    }
  }

  @Test
  public void frameSplitWithNextFrameInSecondRead() {
    byte[] a = frame(1024, 1);
    byte[] b = frame(200, 2);
    byte[] first = Arrays.copyOfRange(a, 0, 500);
    byte[] second = concat(Arrays.copyOfRange(a, 500, a.length), b);
    assertBodies(feed(new LengthPrefixedByteArrayProcessor(), first, second), a, b);
  }

  @Test
  public void shortFrameFollowedByLongerFrameInOneRead() {
    byte[] a = frame(5, 1);
    byte[] b = frame(100, 2);
    List<byte[]> results = feed(new LengthPrefixedByteArrayProcessor(), concat(a, b));
    assertThat(sizes(results)).containsExactly(21, 116);
    assertBodies(results, a, b);
  }

  @Test
  public void singleLengthByteThenRestThenAnotherFrame() {
    byte[] a = frame(5, 1);
    byte[] b = frame(40, 2);
    byte[] first = Arrays.copyOfRange(a, 0, 1);
    byte[] second = concat(Arrays.copyOfRange(a, 1, a.length), b);
    List<byte[]> results = feed(new LengthPrefixedByteArrayProcessor(), first, second);
    assertThat(sizes(results)).containsExactly(21, 56);
    assertBodies(results, a, b);
  }
}
