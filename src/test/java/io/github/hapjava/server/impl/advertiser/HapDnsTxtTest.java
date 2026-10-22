package io.github.hapjava.server.impl.advertiser;

import static org.assertj.core.api.Assertions.assertThat;

import java.nio.charset.StandardCharsets;
import java.util.Map;
import org.junit.Test;

public class HapDnsTxtTest {

  @Test
  public void buildPropertiesContainsRequiredHapKeys() {
    Map<String, String> props =
        HapDnsTxt.buildProperties(true, "AA:BB:CC:DD:EE:FF", "Bridge", "ABCD", 3, 2);

    assertThat(props)
        .containsEntry("sf", "1")
        .containsEntry("id", "AA:BB:CC:DD:EE:FF")
        .containsEntry("md", "Bridge")
        .containsEntry("c#", "3")
        .containsEntry("s#", "1")
        .containsEntry("ff", "0")
        .containsEntry("ci", "2")
        .containsEntry("pv", "1.1");
    assertThat(props.get("sh")).isNotBlank();
  }

  @Test
  public void buildPropertiesSetsSfZeroWhenNotDiscoverable() {
    Map<String, String> props =
        HapDnsTxt.buildProperties(false, "AA:BB:CC:DD:EE:FF", "Bridge", "ABCD", 1, 1);
    assertThat(props).containsEntry("sf", "0");
  }

  @Test
  public void encodeTxtUsesLengthPrefixedKeyValuePairs() {
    Map<String, String> props = HapDnsTxt.buildProperties(true, "id", "md", "setup", 1, 1);
    byte[] txt = HapDnsTxt.encodeTxt(props);

    int offset = 0;
    int pairs = 0;
    while (offset < txt.length) {
      int len = txt[offset] & 0xff;
      String pair = new String(txt, offset + 1, len, StandardCharsets.UTF_8);
      assertThat(pair).contains("=");
      offset += 1 + len;
      pairs++;
    }
    assertThat(pairs).isEqualTo(props.size());
    assertThat(offset).isEqualTo(txt.length);
  }
}
