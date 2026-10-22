package io.github.hapjava.server;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.hapjava.server.impl.dnssd.DnssdHomekitAdvertiser;
import org.junit.Test;

public class HomekitAdvertisersTest {

  @Test
  public void isNativeDnssdSupportedMatchesAdvertiserDetection() {
    assertThat(HomekitAdvertisers.isNativeDnssdSupported())
        .isEqualTo(DnssdHomekitAdvertiser.isSupported());
  }

  @Test
  public void nativeDnssdFactoryWorksWhenSupported() {
    org.junit.Assume.assumeTrue(HomekitAdvertisers.isNativeDnssdSupported());
    HomekitAdvertiser advertiser = HomekitAdvertisers.nativeDnssd();
    assertThat(advertiser).isInstanceOf(DnssdHomekitAdvertiser.class);
    advertiser.stop();
  }
}
