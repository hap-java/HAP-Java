package io.github.hapjava.characteristics.impl.lightbulb;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.Map;
import org.junit.Test;

public class Tlv8Test {

  @Test
  public void longValueIsSplitAndJoined() {
    byte[] value = new byte[300];
    value[299] = 7;
    ByteArrayOutputStream out = new ByteArrayOutputStream();
    Tlv8.write(out, 5, value);
    byte[] data = out.toByteArray();

    assertThat(data).hasSize(304);
    assertThat(data[1] & 0xFF).isEqualTo(255);
    assertThat(data[258]).isEqualTo((byte) 45);
    assertThat(Tlv8.decode(data).get(5)).containsExactly(value);
  }

  @Test
  public void separatedItemsOfOneTypeAreAList() {
    Map<Integer, List<byte[]>> tlv = Tlv8.decode(new byte[] {1, 1, 10, 0, 0, 1, 1, 11, 2, 0});

    assertThat(tlv.get(1)).containsExactly(new byte[] {10}, new byte[] {11});
    assertThat(Tlv8.get(tlv, 2)).isEmpty();
    assertThat(Tlv8.get(tlv, 3)).isNull();
  }

  @Test
  public void unsignedIntegersUseTheSmallestLength() {
    assertThat(Tlv8.uint(255)).containsExactly(0xFF);
    assertThat(Tlv8.uint(256)).containsExactly(0x00, 0x01);
    assertThat(Tlv8.uint(70_000)).containsExactly(0x70, 0x11, 0x01, 0x00);
    assertThat(Tlv8.readUint(Tlv8.uint(86_400_000L))).isEqualTo(86_400_000L);
  }
}
