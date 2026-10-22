package io.github.hapjava.server.impl.advertiser;

import static io.github.hapjava.server.impl.crypto.HAPSetupCodeUtils.generateSHA512Hash;

import java.nio.charset.StandardCharsets;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Shared HAP DNS-SD TXT record helpers for JmDNS and native DNSSD advertisers.
 *
 * @author Karel Goderis
 */
public final class HapDnsTxt {

  public static final String SERVICE_TYPE = "_hap._tcp";
  public static final String SERVICE_TYPE_LOCAL = "_hap._tcp.local.";
  public static final String DOMAIN_LOCAL = "local.";

  private HapDnsTxt() {}

  /**
   * Build the standard HAP TXT key/value map.
   *
   * @param discoverable whether the accessory is unpaired / discoverable ({@code sf})
   * @param mac accessory id ({@code id})
   * @param label model / display name ({@code md})
   * @param setupId setup id used to compute {@code sh}
   * @param configurationIndex configuration number ({@code c#})
   * @param category accessory category ({@code ci})
   * @return ordered TXT properties suitable for JmDNS or {@link #encodeTxt(Map)}
   */
  public static Map<String, String> buildProperties(
      boolean discoverable,
      String mac,
      String label,
      String setupId,
      int configurationIndex,
      int category) {
    Map<String, String> props = new LinkedHashMap<>();
    props.put("sf", discoverable ? "1" : "0");
    props.put("id", mac);
    props.put("md", label);
    props.put("sh", generateSHA512Hash(setupId + mac));
    props.put("c#", Integer.toString(configurationIndex));
    props.put("s#", "1");
    props.put("ff", "0");
    props.put("ci", Integer.toString(category));
    props.put("pv", "1.1");
    return props;
  }

  /**
   * Encode TXT as DNS-SD wire format: a sequence of length-prefixed {@code key=value} strings (RFC
   * 6763).
   *
   * @param props HAP TXT key/value pairs
   * @return DNS-SD TXT RDATA bytes
   * @throws IllegalArgumentException if any encoded pair exceeds 255 bytes
   */
  public static byte[] encodeTxt(Map<String, String> props) {
    int size = 0;
    for (Map.Entry<String, String> e : props.entrySet()) {
      String pair = e.getKey() + "=" + e.getValue();
      byte[] bytes = pair.getBytes(StandardCharsets.UTF_8);
      if (bytes.length > 255) {
        throw new IllegalArgumentException("TXT pair too long: " + e.getKey());
      }
      size += 1 + bytes.length;
    }
    byte[] out = new byte[size];
    int offset = 0;
    for (Map.Entry<String, String> e : props.entrySet()) {
      byte[] bytes = (e.getKey() + "=" + e.getValue()).getBytes(StandardCharsets.UTF_8);
      out[offset++] = (byte) bytes.length;
      System.arraycopy(bytes, 0, out, offset, bytes.length);
      offset += bytes.length;
    }
    return out;
  }
}
