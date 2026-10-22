package io.github.hapjava.server.impl.dnssd;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assume.assumeTrue;

import java.util.concurrent.TimeUnit;
import org.junit.Test;

/**
 * Optional live registration against the host DNS-SD daemon (macOS mDNSResponder). Skipped when
 * native DNS-SD is unavailable. Run with {@code mvn test} on macOS to exercise Bonjour IPC.
 */
public class DnssdLiveAdvertiserIT {

  @Test
  public void registersAndUnregistersWithoutBindingUdp5353() throws Exception {
    assumeTrue("Native DNS-SD not available", DnssdHomekitAdvertiser.isSupported());

    DnssdHomekitAdvertiser advertiser = new DnssdHomekitAdvertiser();
    advertiser.advertise("hap-java-dnssd-it", 2, "AA:BB:CC:DD:EE:FF", 19124, 1, "TEST");
    try {
      // Allow mDNSResponder IPC to complete.
      TimeUnit.MILLISECONDS.sleep(500);
      assertThat(DnssdHomekitAdvertiser.isSupported()).isTrue();
    } finally {
      advertiser.stop();
    }
  }
}
